package com.example.filmoviapp1.presentation.movie.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmoviapp1.domain.model.Movie
import com.example.filmoviapp1.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel(){

    private val _movie = MutableStateFlow<Movie?>(null)
    val movie = _movie.asStateFlow()

    fun loadMovie(id: Int) {
        viewModelScope.launch(Dispatchers.IO){
            _movie.value = repository.getMovieById(id)
        }
    }

    fun updateMovieImage(imageUri: String) {
        _movie.value?.let { movie ->
            val updated = movie.copy(imageUri = imageUri)
            viewModelScope.launch(Dispatchers.IO) {
                repository.updateMovie(updated)
                _movie.value = updated
            }
        }
    }
}