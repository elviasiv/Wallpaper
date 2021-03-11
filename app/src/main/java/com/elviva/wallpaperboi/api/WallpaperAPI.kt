package com.elviva.wallpaperboi.api

import com.elviva.wallpaperboi.models.Wallpaper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WallpaperAPI {

    @GET("/photos")
    suspend fun getWallpaper(@Query("client_id") client_id: String): Response<List<Wallpaper>>

}