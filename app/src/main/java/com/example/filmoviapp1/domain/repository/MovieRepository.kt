package com.example.filmoviapp1.domain.repository

import com.example.filmoviapp1.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getAllMovies(): Flow<List<Movie>>

    suspend fun getMovieById(id: Int): Movie?

    suspend fun insertMovie(movie: Movie): Long

    suspend fun updateMovie(movie: Movie)

    suspend fun deleteMovieById(id: Int)

    fun searchMovie(query: String): Flow<List<Movie>>
}