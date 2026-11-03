package com.example.carrucel.domain.usercase

import com.example.carrucel.domain.repository.MovieRepository
import javax.inject.Inject

class GetMoviesByStarsUseCase @Inject constructor(private val repo: MovieRepository) {
    operator fun invoke(stars: Int) = repo.getMoviesByStars(stars = stars)
}