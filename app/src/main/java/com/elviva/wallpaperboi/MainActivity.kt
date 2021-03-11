package com.elviva.wallpaperboi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.elviva.wallpaperboi.activities.BaseActivity
import com.elviva.wallpaperboi.activities.FavoritesActivity
import com.elviva.wallpaperboi.activities.WallpaperDetailsActivity
import com.elviva.wallpaperboi.adapters.WallpaperAdapter
import com.elviva.wallpaperboi.databinding.ActivityMainBinding
import com.elviva.wallpaperboi.models.Wallpaper
import com.elviva.wallpaperboi.util.Constants
import com.elviva.wallpaperboi.util.Constants.CLIENT_ID
import com.elviva.wallpaperboi.util.RetrofitInstance
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var wallpaperAdapter: WallpaperAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadData()
        setupRecyclerView()

        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible = true
            val response = try {
                RetrofitInstance.api.getWallpaper(CLIENT_ID)
            } catch (e: IOException){
                Log.e("MainActivity", "IOException, might not have internet")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            } catch (e: HttpException){
                Log.e("MainActivity", "HTTPException, unexpected response")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            }

            if(response.isSuccessful && response.body() != null){
                wallpaperAdapter.wallpapers = response.body()!!
            } else {
                Log.e("MainActivity", "Response not successfull")
            }
            binding.progressBar.isVisible = false
        }

        //Setting on click listeners for each wallpaper in recycler view
        //Once pressed on wallpaper we put data in intent and start the details activity
        wallpaperAdapter.setOnClickListener(object: WallpaperAdapter.OnClickListener{
            override fun onClick(position: Int) {
                val intent = Intent(this@MainActivity, WallpaperDetailsActivity::class.java)
                intent.putExtra("ID", wallpaperAdapter.wallpapers[position].id)
                intent.putExtra("USER_NAME", wallpaperAdapter.wallpapers[position].user.name)
                intent.putExtra("DESCRIPTION", wallpaperAdapter.wallpapers[position].description)
                intent.putExtra("ALT_DESCRIPTION", wallpaperAdapter.wallpapers[position].alt_description)
                intent.putExtra("WIDTH", wallpaperAdapter.wallpapers[position].width)
                intent.putExtra("HEIGHT", wallpaperAdapter.wallpapers[position].height)
                intent.putExtra("IMAGE", wallpaperAdapter.wallpapers[position].urls.regular)
                startActivity(intent)
            }

        })

        //Wanted to implement bottom navigation bar but decided to have simple FAB
        //which would take us between favorites and main activity
        binding.fabMain.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }

    }

    //Setting up the recycler view with adapter
    private fun setupRecyclerView(){
        binding.rvWallpapers.apply {
            wallpaperAdapter = WallpaperAdapter(this@MainActivity)
            adapter = wallpaperAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

    }

    //Loading data from shared preferences
    private fun loadData(){
        var sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)
        var gson = Gson()
        var json = sharedPreferences.getString("Favorites list", null)
        val type = object : TypeToken<ArrayList<Wallpaper>>() {}.type
        Constants.mFavoritesList = gson.fromJson(json, type)
    }

}