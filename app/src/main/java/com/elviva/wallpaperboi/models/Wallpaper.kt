package com.elviva.wallpaperboi.models

//Data we get from API
//We can also add a lot more, depends what we need
//For this I only chose not too many
data class Wallpaper(
    val alt_description: String?,
    val description: String?,
    val id: String,
    val width: Int,
    val height: Int,
    val urls: Urls,
    val user: User
)