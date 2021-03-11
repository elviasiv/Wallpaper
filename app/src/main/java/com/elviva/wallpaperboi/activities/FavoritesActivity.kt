package com.elviva.wallpaperboi.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.elviva.wallpaperboi.adapters.FavoriteWallpapersAdapter
import com.elviva.wallpaperboi.databinding.ActivityFavoritesBinding
import com.elviva.wallpaperboi.util.Constants.mFavoritesList

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var favoriteWallpaperAdapter: FavoriteWallpapersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        //fab on click to take us back to main activity
        binding.fabFavorites.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        //In favorite wallpapers tab we can still press on selected wallpaper and find out it's details
        favoriteWallpaperAdapter.setOnClickListener(object: FavoriteWallpapersAdapter.OnClickListener{
            override fun onClick(position: Int) {
                val intent = Intent(this@FavoritesActivity, WallpaperDetailsActivity::class.java)
                intent.putExtra("ID", mFavoritesList[position].id)
                intent.putExtra("USER_NAME", mFavoritesList[position].user.name)
                intent.putExtra("DESCRIPTION", mFavoritesList[position].description)
                intent.putExtra("ALT_DESCRIPTION", mFavoritesList[position].alt_description)
                intent.putExtra("WIDTH", mFavoritesList[position].width)
                intent.putExtra("HEIGHT", mFavoritesList[position].height)
                intent.putExtra("IMAGE", mFavoritesList[position].urls.regular)
                startActivity(intent)
            }

        })
    }

    //Setting up recycler view with adapter
    private fun setupRecyclerView(){
        binding.rvFavoriteWallpapers.apply {
            favoriteWallpaperAdapter = FavoriteWallpapersAdapter(this@FavoritesActivity)
            adapter = favoriteWallpaperAdapter
            layoutManager = LinearLayoutManager(this@FavoritesActivity)
        }

    }

}