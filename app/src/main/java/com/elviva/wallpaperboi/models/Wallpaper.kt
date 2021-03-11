package com.elviva.wallpaperboi.models

data class Wallpaper(
    val alt_description: String?,
    val description: String?,
    val id: String,
    val width: Int,
    val height: Int,
    val urls: Urls,
    val user: User
)