package com.example.filmoviapp1.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmoviapp1.domain.model.Movie
import com.example.filmoviapp1.domain.useCases.DeleteMovie
import com.example.filmoviapp1.domain.useCases.GetMovies
import com.example.filmoviapp1.domain.useCases.SearchMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovies: GetMovies,
    private val deleteMovie: DeleteMovie,
    private val searchMovie: SearchMovie
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie = _selectedMovie.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _uiState.value = MovieUiState.Loading
            try {
                getMovies()
                    .collect { movies ->
                        _uiState.value = if (movies.isEmpty()) {
                            MovieUiState.Empty
                        } else {
                            MovieUiState.Success(movies)
                        }
                    }
            } catch (e: Exception) {
                _uiState.value = MovieUiState.Error(e.message ?: "An Error Occurred")
            }
        }
    }

    fun deleteMovie(id: Int) {
        viewModelScope.launch {
            try {
                deleteMovie(id)
            } catch (error: Exception) {
                _uiState.value = MovieUiState.Error(
                    error.message ?: "An Error Occurred When Deleting the Movie"
                )
            }
        }
    }

    fun selectedMovie(movie: Movie) {
        _selectedMovie.value = movie
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query

        if (query.isEmpty()) {
            loadMovies()
        } else {
            viewModelScope.launch {
                try {
                    searchMovie(query)
                        .collect {movies ->
                            _uiState.value = if (movies.isEmpty()) {
                                MovieUiState.Empty
                            } else {
                                MovieUiState.Success(movies)
                            }
                        }
                } catch (e: Exception) {
                    _uiState.value = MovieUiState.Error(
                        e.message ?: "An Error Occurred While Looking Up a Movie"
                    )
                }
            }
        }
    }

    fun clearError() {
        loadMovies()
    }

}