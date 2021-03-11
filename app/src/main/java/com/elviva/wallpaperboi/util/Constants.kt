package com.elviva.wallpaperboi.util

import com.elviva.wallpaperboi.models.Wallpaper

object Constants {

    const val CLIENT_ID = "f9SijCo0wU9DWlxEffVXhWZhDr53zwCSDzANsl0xpt0" //API key
    const val BASE_URL = "https://api.unsplash.com"                     //Base url for unsplash

    var mFavoritesList: ArrayList<Wallpaper> = ArrayList()              //Favorite wallpaper list that we save in shared preferences
}