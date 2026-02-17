package com.example.filmoviapp1.domain.useCases

import com.example.filmoviapp1.domain.model.Movie
import com.example.filmoviapp1.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovies @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<List<Movie>> {
        return repository.getAllMovies()
    }
}