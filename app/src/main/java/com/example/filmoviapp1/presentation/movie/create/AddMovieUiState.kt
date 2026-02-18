package com.example.filmoviapp1.presentation.movie.create

sealed class AddMovieUiState {

    object Idle: AddMovieUiState()
    object Loading: AddMovieUiState()
    object Success: AddMovieUiState()
    data class Error(val message: String): AddMovieUiState()
}