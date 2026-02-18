package com.example.filmoviapp1.domain.model

data class Movie(
    val id: Int = 0,
    val name: String,
    val director: String,
    val movieLength: String = "",
    val category: String = "",
    val imageUri: String? = null,
    val releaseDate: String
) {
}