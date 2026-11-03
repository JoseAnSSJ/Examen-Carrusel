package com.example.carrucel.domain.usercase

import com.example.carrucel.domain.repository.MovieRepository
import javax.inject.Inject

class GetMoviesByGenreUseCase @Inject constructor(private val repo: MovieRepository) {
    operator fun invoke(genre: String) = repo.getMoviesByGenre(genre = genre)
}