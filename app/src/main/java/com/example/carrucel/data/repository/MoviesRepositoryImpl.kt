package com.example.carrucel.data.repository

import com.example.carrucel.core.Resource
import com.example.carrucel.data.mapper.toDomain
import com.example.carrucel.data.remote.MovieApiService
import com.example.carrucel.domain.model.Movie
import com.example.carrucel.domain.repository.MovieRepository
import com.example.carrucel.utils.safeApiFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val api: MovieApiService
) : MovieRepository {
    override fun getMovies(): Flow<Resource<List<Movie>>> {
        return safeApiFlow { api.getMovies().toDomain() }
    }

    override fun getMoviesByStars(stars: Int): Flow<Resource<List<Movie>>> {
        return safeApiFlow { api.getMoviesByStars(stars = stars).toDomain() }
    }

    override fun getMoviesByYear(year: Int): Flow<Resource<List<Movie>>> {
        return safeApiFlow { api.getMoviesByYear(year = year).toDomain() }
    }

    override fun getMoviesByGenre(genre: String): Flow<Resource<List<Movie>>> {
        return safeApiFlow { api.getMoviesByGenre(genre = genre).toDomain() }
    }

    override fun getMoviesById(id: Int): Flow<Resource<Movie>> {
        return safeApiFlow { api.getMoviesById(id = id).toDomain() }
    }


}