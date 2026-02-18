package com.example.filmoviapp1.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.filmoviapp1.domain.model.Movie

@Composable
fun MoviesList(
    movies: List<Movie>,
    onMovieClick: (Movie) -> Unit,
    onDeleteClick: (Movie) -> Unit,
    cardColor: Color = Color.Black,
    titleColor: Color = Color.White,
    subtitleColor: Color = Color.LightGray,
    deleteColor: Color = Color.Red
) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(movies, key = { it.id }) { movie ->
            MovieCard(
                movie = movie,
                onMovieClick = { onMovieClick(movie) },
                onDeleteClick = { onDeleteClick(movie) },
                cardColor = cardColor,
                titleColor = titleColor,
                subtitleColor = subtitleColor,
                deleteColor = deleteColor
            )
        }
    }
}