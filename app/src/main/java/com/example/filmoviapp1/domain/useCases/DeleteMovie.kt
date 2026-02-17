package com.example.filmoviapp1.domain.useCases

import com.example.filmoviapp1.domain.repository.MovieRepository
import javax.inject.Inject

class DeleteMovie @Inject constructor(
    private val repository: MovieRepository

){
    suspend operator fun invoke(id: Int): Result<Unit> {
        return try {
            repository.deleteMovieById(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}