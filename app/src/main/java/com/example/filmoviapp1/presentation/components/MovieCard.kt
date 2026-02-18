package com.example.filmoviapp1.presentation.components

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.filmoviapp1.domain.model.Movie

@Composable
fun MovieCard(
    movie: Movie,
    onMovieClick: () -> Unit,
    onDeleteClick: () -> Unit,
    cardColor: Color = Color.Black,
    titleColor: Color = Color.White,
    subtitleColor: Color = Color.LightGray,
    deleteColor: Color = Color.Red
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onMovieClick() }
            .padding(horizontal = 8.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Slika filma
            if (movie.imageUri != null) {
                AsyncImage(
                    model = movie.imageUri,
                    contentDescription = movie.name,
                    modifier = Modifier
                        .size(60.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Surface(
                    modifier = Modifier
                        .size(50.dp)
                        .padding(end = 16.dp),
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = movie.name.first().toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = titleColor
                        )
                    }
                }
            }

            // Info o filmu
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = movie.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = titleColor,
                )

                Text(
                    text = movie.director,
                    fontSize = 12.sp,
                    color = subtitleColor
                )

                Text(
                    text = movie.releaseDate.toString(),
                    fontSize = 12.sp,
                    color = subtitleColor
                )
            }

            IconButton(onClick = onDeleteClick) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "Delete",
                    tint = deleteColor
                )
            }
        }
    }
}