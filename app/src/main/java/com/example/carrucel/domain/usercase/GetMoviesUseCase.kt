package com.example.carrucel.domain.usercase

import com.example.carrucel.domain.repository.MovieRepository
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val repo: MovieRepository) {
    operator fun invoke() = repo.getMovies()
}