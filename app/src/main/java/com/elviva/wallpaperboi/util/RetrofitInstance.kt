package com.elviva.wallpaperboi.util

import com.elviva.wallpaperboi.api.WallpaperAPI
import com.elviva.wallpaperboi.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//Retrofit instance where we can call the api
object RetrofitInstance {

    val api: WallpaperAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WallpaperAPI::class.java)
    }

}