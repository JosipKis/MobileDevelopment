package com.example.filmoviapp1.presentation

import com.example.filmoviapp1.domain.model.Movie

sealed class MovieUiState {
    object Loading: MovieUiState()
    data class Success(val movies: List<Movie>): MovieUiState()
    data class Error(val message: String): MovieUiState()
    object Empty: MovieUiState()
}