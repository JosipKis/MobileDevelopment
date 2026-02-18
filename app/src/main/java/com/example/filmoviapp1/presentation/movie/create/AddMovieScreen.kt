package com.example.filmoviapp1.presentation.movie.create

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.filmoviapp1.data.database.entity.MovieCategory
import com.example.filmoviapp1.domain.model.Movie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMovieScreen (
    navController: NavHostController,
    viewModel: AddMovieViewModel = hiltViewModel(),
    movie: Movie? = null,
    movieIdForEdit: Int? = null,
    onBackClick: () -> Unit = {},
    onSaveSuccess: () -> Unit = {},
    onPhotoClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val name by viewModel.name.collectAsStateWithLifecycle()
    val director by viewModel.director.collectAsStateWithLifecycle()
    val category by viewModel.category.collectAsStateWithLifecycle()
    val movieLength by viewModel.movieLength.collectAsStateWithLifecycle()
    val releaseDate by viewModel.releaseDate.collectAsStateWithLifecycle()
    val imageUri by viewModel.imageUri.collectAsStateWithLifecycle()

    val categories = MovieCategory.entries.toTypedArray()

    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val photoPathFlow = savedStateHandle?.getStateFlow<String?>("photoPath", null)
    val photoPath by photoPathFlow?.collectAsStateWithLifecycle() ?: remember { mutableStateOf(null) }

    LaunchedEffect(photoPath) {
        photoPath?.let {
            viewModel.setImageUri(it)
            savedStateHandle?.remove<String>("photoPath")
        }
    }

    LaunchedEffect(movieIdForEdit) {
        if (movieIdForEdit != null) {
            viewModel.loadMovieForEditing(movieIdForEdit)
        }
    }

    LaunchedEffect(movie) {
        movie?.let {
            viewModel.loadMovieForEditDirect(it)
        }
    }

    LaunchedEffect(uiState) {
        if (uiState is AddMovieUiState.Success) {
            onSaveSuccess()
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (movieIdForEdit != null) "Edit Movie" else "Add Movie"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            when (uiState) {
                is AddMovieUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is AddMovieUiState.Error -> {
                    Text(
                        text = (uiState as AddMovieUiState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                else -> {
                    TextField(
                        value = name,
                        onValueChange = { viewModel.setName(it) },
                        label = {Text("Name*")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        singleLine = true
                    )

                    TextField(
                        value = director,
                        onValueChange = { viewModel.setDirector(it) },
                        label = {Text("Director*")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        singleLine = true
                    )

                    var expanded by remember { mutableStateOf(false) }

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        TextField(
                            value = category,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Category*") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            categories.forEach {
                                DropdownMenuItem(
                                    text = { Text(it.display) },
                                    onClick = {
                                        viewModel.setCategory(it.display)
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    TextField(
                        value = movieLength,
                        onValueChange = { viewModel.setMovieLength(it) },
                        label = {Text("Movie Length*")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        singleLine = true
                    )

                    TextField(
                        value = releaseDate,
                        onValueChange = { viewModel.setReleaseDate(it) },
                        label = {Text("Release Date*")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        singleLine = true
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Button(
                        onClick = onPhotoClick,
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        Icon(Icons.Filled.PhotoCamera,
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text("Take Photo +")
                    }

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Button (
                        onClick = {viewModel.saveMovie()},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text("Save")
                    }

                }
            }
        }
    }

}