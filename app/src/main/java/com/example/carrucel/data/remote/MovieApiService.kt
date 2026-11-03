package com.example.carrucel.data.remote

import com.example.carrucel.data.remote.dto.MoviesResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApiService {

    @GET("api-movies")
    suspend fun getMovies(): List<MoviesResponseDTO>

    @GET("api-movies/stars/{stars}")
    suspend fun getMoviesByStars(
        @Path("stars") stars: Int
    ): List<MoviesResponseDTO>

    @GET("api-movies/year/{year}")
    suspend fun getMoviesByYear(
        @Path("year") year: Int
    ): List<MoviesResponseDTO>

    @GET("api-movies/genre/{genre}")
    suspend fun getMoviesByGenre(
        @Path("genre") genre: String
    ): List<MoviesResponseDTO>

    @GET("api-movies/{id}")
    suspend fun getMoviesById(
        @Path("id") id: Int
    ): MoviesResponseDTO
}