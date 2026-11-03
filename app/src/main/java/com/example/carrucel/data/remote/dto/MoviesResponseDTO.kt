package com.example.carrucel.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MoviesResponseDTO (
    val id: Int = 0,
    val title: String? = null,
    val description: String? = null,
    val year: Int? = null,
    @SerializedName("image_url") val imageUrl: String? = null,
    val genre: List<String>? = null,
    val stars: Double? = null
)
