package com.example.filmoviapp1.presentation.movie

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.filmoviapp1.domain.model.Movie
import com.example.filmoviapp1.presentation.MovieUiState
import com.example.filmoviapp1.presentation.MovieViewModel
import com.example.filmoviapp1.presentation.components.EmptyScreen
import com.example.filmoviapp1.presentation.components.ErrorScreen
import com.example.filmoviapp1.presentation.components.MoviesList
import com.example.filmoviapp1.presentation.components.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen (
    viewModel: MovieViewModel = hiltViewModel(),
    onAddClick: () -> Unit = {},
    onEditClick: (Int) -> Unit = {},
    onMovieClick: (Movie) -> Unit = {},
    onNavigateToDetail: (Movie) -> Unit = {}
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    Scaffold (
        topBar = {
            TopAppBar(
                title  = {
                    Text("My Movie Library")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Add, "Add Movie +")
            }
        }
    ) {
            paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { viewModel.updateSearchQuery(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            // Content
            when (uiState) {
                is MovieUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is MovieUiState.Success -> {
                    MoviesList(
                        movies = (uiState as MovieUiState.Success).movies,
                        onMovieClick = { movie ->
                            viewModel.selectedMovie(movie)
                            onNavigateToDetail(movie)
                        },
                        onDeleteClick = { movie ->
                            viewModel.deleteMovie(movie.id)
                        }
                    )
                }

                is MovieUiState.Error -> {
                    ErrorScreen(
                        message = (uiState as MovieUiState.Error).message,
                        onRetry = viewModel::clearError
                    )
                }

                is MovieUiState.Empty -> {
                    EmptyScreen (onAddClick = onAddClick)
                }
            }
        }
    }
}