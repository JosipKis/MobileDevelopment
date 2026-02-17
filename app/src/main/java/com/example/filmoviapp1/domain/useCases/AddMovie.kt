package com.example.filmoviapp1.domain.useCases

import com.example.filmoviapp1.domain.model.Movie
import com.example.filmoviapp1.domain.repository.MovieRepository
import javax.inject.Inject

class AddMovie @Inject constructor(
    private val repository: MovieRepository

) {
    suspend operator fun invoke(movie: Movie) : Result<Long>{
        return try {
            val id = repository.insertMovie(movie)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}