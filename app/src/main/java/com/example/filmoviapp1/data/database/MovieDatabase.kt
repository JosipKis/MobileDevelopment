package com.example.filmoviapp1.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.filmoviapp1.data.database.converters.Converters
import com.example.filmoviapp1.data.database.dao.MovieDao
import com.example.filmoviapp1.data.database.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object{
        const val DATABASE_NAME = "movies_db"
    }
}