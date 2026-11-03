package com.example.carrucel.data.mapper

import com.example.carrucel.data.remote.dto.MoviesResponseDTO
import com.example.carrucel.domain.model.Movie

fun MoviesResponseDTO.toDomain() = Movie(
    id = id,
    title = title.orEmpty(),
    description = description.orEmpty(),
    year = year ?: 0,
    imageUrl = imageUrl.orEmpty(),
    genres = genre.orEmpty(),
    rating = stars ?: 0.0
)

fun List<MoviesResponseDTO>.toDomain(): List<Movie> = map { it.toDomain() }
