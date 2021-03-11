package com.elviva.wallpaperboi.util

import com.elviva.wallpaperboi.api.WallpaperAPI
import com.elviva.wallpaperboi.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//Custom retrofit object singleton
object RetrofitInstance {

    //We initialize it by lazy so that it wont be initialized right away
    //instead it will be initialize when we first access this api
    val api: WallpaperAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WallpaperAPI::class.java)
    }

}