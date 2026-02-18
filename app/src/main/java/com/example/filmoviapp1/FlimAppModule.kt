package com.example.filmoviapp1

import android.content.Context
import androidx.room.Room
import com.example.filmoviapp1.data.database.MovieDatabase
import com.example.filmoviapp1.data.database.dao.MovieDao
import com.example.filmoviapp1.data.repository.MovieRepositoryImplementation
import com.example.filmoviapp1.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FilmAppModule {

    @Singleton
    @Provides
    fun provideMovieDatabase(
        @ApplicationContext context: Context
    ): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            MovieDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(database: MovieDatabase) = database.movieDao()

    @Singleton
    @Provides
    fun provideMovieRepository(dao: MovieDao): MovieRepository {
        return MovieRepositoryImplementation(dao)
    }

}