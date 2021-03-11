package com.elviva.wallpaperboi.api

import com.elviva.wallpaperboi.models.Wallpaper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WallpaperAPI {

    //Getting wallpapers from API
    //Sending our API key with query
    //Getting a list of wallpapers as a response
    //Using suspend because this call will be launched in another thread
    @GET("/photos")
    suspend fun getWallpaper(@Query("client_id") client_id: String): Response<List<Wallpaper>>

}