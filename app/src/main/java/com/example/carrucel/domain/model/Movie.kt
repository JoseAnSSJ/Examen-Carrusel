package com.example.carrucel.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val description: String,
    val year: Int,
    val imageUrl: String,
    val genres: List<String>,
    val rating: Double
)