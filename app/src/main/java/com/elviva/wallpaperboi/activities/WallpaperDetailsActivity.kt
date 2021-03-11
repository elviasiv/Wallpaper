package com.elviva.wallpaperboi.activities

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.elviva.wallpaperboi.R
import com.elviva.wallpaperboi.databinding.ActivityWallpaperDetailsBinding
import com.elviva.wallpaperboi.models.Urls
import com.elviva.wallpaperboi.models.User
import com.elviva.wallpaperboi.models.Wallpaper
import com.elviva.wallpaperboi.util.Constants.mFavoritesList
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.MalformedURLException
import java.net.URISyntaxException
import java.net.URL

//In this activity all chosen wallpaper data is displayed
//
class WallpaperDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWallpaperDetailsBinding

    private var id: String? = ""
    private var name: String? = ""
    private var description: String? = ""
    private var altDescription: String? = ""
    private var width: Int? = null
    private var height: Int? = null
    private var imageUrl: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWallpaperDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUi()

        binding.btnAddToFavorites.setOnClickListener {
            addWallpaperToFavorites()
        }

        binding.btnShare.setOnClickListener {
            shareItem(imageUrl)
        }
    }

    fun shareItem(url: String?) {

        Picasso.get().load(url).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: LoadedFrom?) {
                val i = Intent(Intent.ACTION_SEND)
                i.type = "image/*"
                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap!!))
                startActivity(Intent.createChooser(i, "Share Image"))
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
        })
    }

    fun getLocalBitmapUri(bmp: Bitmap): Uri? {
        var bmpUri: Uri? = null
        try {
            val file = File(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "share_image_" + System.currentTimeMillis() + ".png"
            )
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.close()
            bmpUri = Uri.fromFile(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmpUri
    }

    private fun shareImage(){
        //val uri: Uri = Uri.parse(imageUrl)
        //Log.i("DetailsActivity", "URI = $uri")
        val myUrlStr = imageUrl
        val url: URL
        val uri: Uri
        try {
            url = URL(myUrlStr)
            uri = Uri.parse(url.toURI().toString())

            Log.i("DetailsActivity", "URI = $uri")
            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, uri)
                type = "text/html"
            }
            startActivity(Intent.createChooser(shareIntent, "send to"))
        } catch (e1: MalformedURLException) {
            e1.printStackTrace()
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }



    }

    //Getting data from intent and setting up the UI
    private fun setupUi(){
        id = intent.getStringExtra("ID")
        name = intent.getStringExtra("USER_NAME")
        description = intent.getStringExtra("DESCRIPTION")
        altDescription = intent.getStringExtra("ALT_DESCRIPTION")
        width = intent.getIntExtra("WIDTH", 1)
        height = intent.getIntExtra("HEIGHT", 1)
        imageUrl = intent.getStringExtra("IMAGE")

        Log.i("DetailsActivity", "image url = $imageUrl ")

        binding.tvName.text = name
        if(description == null){
            binding.tvDescription.text = altDescription
        } else {
            binding.tvDescription.text = description
        }
        binding.tvWidth.text = width.toString()
        binding.tvHeight.text = height.toString()

        Glide
            .with(this@WallpaperDetailsActivity)
            .load(imageUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.ivFullWallpaper)
    }

    private fun addWallpaperToFavorites(){
        //Checking if the selected image is already in favorites so it doesn't add duplicates
        for (i in mFavoritesList.indices){
            if(mFavoritesList[i].id == id) {
                Toast.makeText(this, "This image is already in favorites", Toast.LENGTH_SHORT).show()
                return
            }
        }

        val image = Urls(imageUrl!!)
        val user = User(name!!)
        val favoriteWallpaper = Wallpaper(  //Creating wallpaper with given attributes,
            altDescription!!,
            description,
            id!!,
            width!!,
            height!!,
            image,
            user
        )
        mFavoritesList.add(favoriteWallpaper) //adding it to the list

        saveData() //and saving the list to shared preferences

        Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show()
    }

    //Saving data in shared preferences
    private fun saveData(){
        val sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)
        val edit: SharedPreferences.Editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(mFavoritesList)
        edit.putString("Favorites list", json)
        edit.apply()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}