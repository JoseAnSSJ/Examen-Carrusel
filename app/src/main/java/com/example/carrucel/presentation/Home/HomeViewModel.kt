package com.example.carrucel.presentation.Home

import androidx.lifecycle.viewModelScope
import com.example.carrucel.core.Resource
import com.example.carrucel.domain.model.Movie
import com.example.carrucel.domain.usercase.GetMoviesByGenreUseCase
import com.example.carrucel.domain.usercase.GetMoviesByStarsUseCase
import com.example.carrucel.domain.usercase.GetMoviesByYearUseCase
import com.example.carrucel.domain.usercase.GetMoviesUseCase
import com.example.carrucel.presentation.BaseViewModel
import com.example.carrucel.utils.GenreEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMovies: GetMoviesUseCase,
    private val getByStars: GetMoviesByStarsUseCase,
    private val getByYear: GetMoviesByYearUseCase,
    private val getByGenre: GetMoviesByGenreUseCase
) : BaseViewModel() {

    private val _featured = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val featured = _featured.asStateFlow()

    private val _topRated = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val topRated = _topRated.asStateFlow()

    private val _newItems = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val newItems = _newItems.asStateFlow()

    private val _genreMovies = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val genreMovies = _genreMovies.asStateFlow()

    private val _selectedGenre = MutableStateFlow(GenreEnum.DRAMA)
    val selectedGenre = _selectedGenre.asStateFlow()

    private var genreJob: Job? = null

    init {
        loadFeatured()
        loadTopRated()
        loadNew()
        loadGenre(genreEnum = GenreEnum.DRAMA)
    }

    fun loadFeatured() {
        viewModelScope.launch {
            getMovies().collect { result ->
                _featured.value = result
            }
        }
    }

    fun loadTopRated() {
        viewModelScope.launch {
            getByStars(stars = 5).collect { result ->
                _topRated.value = result
            }
        }
    }

    fun loadNew() {
        viewModelScope.launch {
            getByYear(year = 2026).collect { result ->
                _newItems.value = result
            }
        }
    }

    fun loadGenre(genreEnum: GenreEnum) {
        _selectedGenre.value = genreEnum
        genreJob?.cancel()
        genreJob = viewModelScope.launch {
            getByGenre(genre = genreEnum.name).collect { result ->
                _genreMovies.value = result
            }
        }
    }


    override fun refresh() {
        loadingRefresh {
            loadFeatured()
            loadTopRated()
            loadNew()
            loadGenre(genreEnum = GenreEnum.DRAMA)
        }
    }
}