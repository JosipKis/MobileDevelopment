package com.example.filmoviapp1.data.repository

import com.example.filmoviapp1.data.database.dao.MovieDao
import com.example.filmoviapp1.data.mapper.toDomain
import com.example.filmoviapp1.data.mapper.toEntity
import com.example.filmoviapp1.domain.model.Movie
import com.example.filmoviapp1.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImplementation(
    private val movieDao: MovieDao
): MovieRepository {

    // Koristio sam = umjesto return samo da probam, Java je imala pravo kad je koristila return
    override fun getAllMovies(): Flow<List<Movie>> =
        movieDao.getAllMovies().map { movieEntities ->
            movieEntities.map { it.toDomain() }
        }

    override suspend fun getMovieById(id: Int): Movie? {
        return movieDao.getMovieById(id)?.toDomain()
    }

    override suspend fun insertMovie(movie: Movie): Long {
        return movieDao.insertMovie(movie.toEntity())
    }

    override suspend fun updateMovie(movie: Movie) {
        movieDao.updateMovie(movie.toEntity())
    }

    override suspend fun deleteMovieById(id: Int) {
        movieDao.deleteMovieById(id)
    }

    override fun searchMovie(query: String): Flow<List<Movie>> {
        return movieDao.searchMovie("%$query").map {
            movieEntities ->
            movieEntities.map { it.toDomain() }
        }
    }
}