package com.example.filmoviapp1.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val director: String,
    val movieLength: String = "",
    val category: String = "",
    val imageUri: String? = null,
    val releaseDate: String
)