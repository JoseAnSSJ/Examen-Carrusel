package com.example.carrucel.domain.repository

import com.example.carrucel.core.Resource
import com.example.carrucel.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(): Flow<Resource<List<Movie>>>
    fun getMoviesByStars(stars: Int): Flow<Resource<List<Movie>>>
    fun getMoviesByYear(year: Int):Flow<Resource<List<Movie>>>
    fun getMoviesByGenre(genre: String): Flow<Resource<List<Movie>>>
    fun getMoviesById(id: Int): Flow<Resource<Movie>>
}