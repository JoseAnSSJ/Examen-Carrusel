package com.example.carrucel.domain.usercase

import com.example.carrucel.domain.repository.MovieRepository
import javax.inject.Inject

class GetMoviesByYearUseCase @Inject constructor(private val repo: MovieRepository) {
    operator fun invoke(year: Int) = repo.getMoviesByYear(year = year)
}