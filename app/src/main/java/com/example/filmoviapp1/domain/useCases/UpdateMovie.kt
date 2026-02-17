package com.example.filmoviapp1.domain.useCases

import com.example.filmoviapp1.domain.model.Movie
import com.example.filmoviapp1.domain.repository.MovieRepository
import javax.inject.Inject

class UpdateMovie @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke (movie: Movie): Result<Unit>{
        return try {
            repository.updateMovie(movie)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}