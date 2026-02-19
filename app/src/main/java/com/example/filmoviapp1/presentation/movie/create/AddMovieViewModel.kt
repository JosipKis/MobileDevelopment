package com.example.filmoviapp1.presentation.movie.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmoviapp1.domain.model.Movie
import com.example.filmoviapp1.domain.repository.MovieRepository
import com.example.filmoviapp1.domain.useCases.AddMovie
import com.example.filmoviapp1.domain.useCases.UpdateMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddMovieViewModel @Inject constructor(
    private val addMovie: AddMovie,
    private val updateMovie: UpdateMovie,
    private val repository: MovieRepository
) : ViewModel() {
    private var isMovieLoaded: Boolean = false
    private val _uiState = MutableStateFlow<AddMovieUiState>(AddMovieUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _director = MutableStateFlow("")
    val director = _director.asStateFlow()

    private val _category = MutableStateFlow("")
    val category = _category.asStateFlow()

    private val _movieLength = MutableStateFlow("")
    val movieLength = _movieLength.asStateFlow()

    private val _releaseDate = MutableStateFlow<LocalDate?>(null)
    val releaseDate = _releaseDate.asStateFlow()

    private val _imageUri = MutableStateFlow("")
    val imageUri = _imageUri.asStateFlow()

    private var movieId: Int? = null

    fun setName(name: String) {
        _name.value = name
    }

    fun setDirector(director: String) {
         _director.value = director
    }

    fun setCategory(category: String) {
        _category.value = category
    }

    fun setMovieLength(movieLength: String) {
        _movieLength.value = movieLength
    }

    fun setReleaseDate(releaseDate: LocalDate) {
        _releaseDate.value = releaseDate
    }

    fun setImageUri(uri: String) {
        _imageUri.value = uri
    }

    fun loadMovieForEditing(id: Int) {
        if (isMovieLoaded) return
        isMovieLoaded = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movie = repository.getMovieById(id)
                if (movie != null) {
                    _name.value = movie.name
                    _director.value = movie.director
                    _category.value = movie.category
                    _movieLength.value = movie.movieLength
                    _releaseDate.value = movie.releaseDate
                    _imageUri.value = movie.imageUri ?: ""
                    movieId = movie.id
                }
            } catch (e: Exception) {
                _uiState.value = AddMovieUiState.Error("Error loading movie: ${e.message}")
            }
        }
    }
    fun loadMovieForEditDirect(movie: Movie) {
        if (isMovieLoaded) return
        isMovieLoaded = true

        _name.value = movie.name
        _director.value = movie.director
        _category.value = movie.category
        _movieLength.value = movie.movieLength
        _releaseDate.value = movie.releaseDate
        _imageUri.value = movie.imageUri ?: ""
        movieId = movie.id
    }

    fun saveMovie() {
        if (!validateForm()) {
            _uiState.value = AddMovieUiState.Error("Please Fill In All of the Fields")
            return
        }

        viewModelScope.launch {
            _uiState.value = AddMovieUiState.Loading

            val movie = Movie(
                id = movieId ?: 0,
                name = _name.value,
                director = _director.value,
                category = _category.value,
                movieLength = _movieLength.value,
                releaseDate = _releaseDate.value!!,
                imageUri = _imageUri.value,
            )

            val result = if (movieId != null) {
                updateMovie(movie)
            } else {
                addMovie(movie).map {
                    Unit
                }
            }

            result
                .onSuccess {
                    _uiState.value = AddMovieUiState.Success
                }
                .onFailure { error ->
                    _uiState.value = AddMovieUiState.Error(error.message ?: "An Error Occurred")
                }
        }
    }

    private fun validateForm(): Boolean {
        return _name.value.isNotEmpty() &&
                _director.value.isNotEmpty() &&
                _category.value.isNotEmpty() &&
                _movieLength.value.isNotEmpty() &&
                _releaseDate.value != null
    }

    fun resetState() {
        _uiState.value = AddMovieUiState.Idle
    }
}