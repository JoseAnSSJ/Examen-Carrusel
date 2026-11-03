package com.example.carrucel.domain.usercase

import com.example.carrucel.domain.repository.MovieRepository
import javax.inject.Inject

class GetMoviesByIdUseCase  @Inject constructor(private val repo: MovieRepository) {
    operator fun invoke(id: Int) = repo.getMoviesById(id = id)
}