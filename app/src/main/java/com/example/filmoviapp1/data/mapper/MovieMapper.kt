package com.example.filmoviapp1.data.mapper

import com.example.filmoviapp1.data.database.entity.MovieEntity
import com.example.filmoviapp1.domain.model.Movie
fun MovieEntity.toDomain(): Movie = Movie(
    id = id,
    name = name,
    director = director,
    movieLength = movieLength,
    category = category,
    imageUri = imageUri,
    releaseDate =  releaseDate
)

fun Movie.toEntity(): MovieEntity = MovieEntity(
    id = id,
    name = name,
    director = director,
    movieLength = movieLength,
    category = category,
    imageUri = imageUri,
    releaseDate =  releaseDate
)
