package com.example.filmoviapp1.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.filmoviapp1.data.database.dao.MovieDao
import com.example.filmoviapp1.data.database.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object{
        const val DATABASE_NAME = "movies_db"
    }
}