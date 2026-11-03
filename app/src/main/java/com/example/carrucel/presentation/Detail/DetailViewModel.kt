package com.example.carrucel.presentation.Detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrucel.core.Resource
import com.example.carrucel.domain.model.Movie
import com.example.carrucel.domain.usercase.GetMoviesByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMoviesByIdUseCase: GetMoviesByIdUseCase, savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: Int = checkNotNull(savedStateHandle["id"])

    private val _movie = MutableStateFlow<Resource<Movie>>(Resource.Loading)
    val movie = _movie.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            getMoviesByIdUseCase(id = movieId).collect { result ->
                _movie.value = result
            }
        }
    }
}