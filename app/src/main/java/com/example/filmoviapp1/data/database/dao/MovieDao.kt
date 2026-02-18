package com.example.filmoviapp1.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.filmoviapp1.data.database.entity.MovieEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY releaseDate ASC")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovieById(id: Int): MovieEntity?

    @Insert
    suspend fun insertMovie(movieEntity: MovieEntity): Long

    @Update
    suspend fun updateMovie(movieEntity: MovieEntity)

    @Delete
    suspend fun deleteMovieByMovie(movieEntity: MovieEntity)

    @Query("DELETE FROM movies WHERE id=:id")
    suspend fun deleteMovieById(id: Int)

    @Query("SELECT * FROM movies WHERE name LIKE '%' || :query || '%' COLLATE NOCASE")
    fun searchMovie(query: String): Flow<List<MovieEntity>>

}