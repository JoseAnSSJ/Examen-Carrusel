package com.example.carrucel.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

abstract class BaseViewModel : ViewModel() {
    private val _isRefresh = MutableStateFlow(false)
    val isRefresh = _isRefresh.asStateFlow()

    abstract fun refresh()

    protected fun loadingRefresh(block: suspend CoroutineScope.() -> Unit) {
        if (_isRefresh.value) return
        viewModelScope.launch {
            _isRefresh.value = true
            try {
                supervisorScope {
                    block()
                }

            } finally {
                _isRefresh.value = false
            }
        }
    }
}