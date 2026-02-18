package com.example.filmoviapp1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.filmoviapp1.domain.useCases.AddMovie
import com.example.filmoviapp1.presentation.components.CameraScreen
import com.example.filmoviapp1.presentation.movie.MovieScreen
import com.example.filmoviapp1.presentation.movie.create.AddMovieScreen
import com.example.filmoviapp1.presentation.movie.detail.MovieDetailScreen

sealed class Screen(val route: String) {
    object Movies : Screen("movies_screen")
    object MovieDetail : Screen("movie_detail_screen/{movieId}") {
        fun createRoute(movieId: Int) = "movie_detail_screen/$movieId"
    }
    object AddMovie: Screen("add_movie_screen")
    object EditMovie: Screen("edit_movie/{movieId}") {
        fun createRoute(movieId: Int) = "edit_movie/$movieId"
    }

    object Camera: Screen("camera_screen/{movieId}") {
        fun createRoute(movieId: Int) = "camera_screen/$movieId"
    }
}

@Composable
fun Navigation () {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Screen.Movies.route
    ) {
        composable(Screen.Movies.route) {
            MovieScreen(
                onAddClick = {
                    navController.navigate(Screen.AddMovie.route)
                },
                onNavigateToDetail = { movie ->
                    navController.navigate(Screen.MovieDetail.createRoute(movie.id))
                }
            )
        }

        composable(Screen.AddMovie.route) {
            AddMovieScreen (
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveSuccess = {
                    navController.popBackStack()
                }
            )
        }

        composable (
            Screen.MovieDetail.route,
            arguments = listOf(
                navArgument("movieId") {type = NavType.IntType}
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId")
            MovieDetailScreen(
                movieId = movieId!!,
                onBackClick = {
                    navController.popBackStack()
                },
                onEditClick = { movie ->
                    navController.navigate(Screen.EditMovie.createRoute(movie.id))
                },
                onPhotoClick = {
                    navController.navigate(Screen.Camera.createRoute(movieId))
                }
            )
        }

        composable (Screen.AddMovie.route) {
            AddMovieScreen (
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveSuccess = {
                    navController.popBackStack()
                }
            )
        }

        composable (
            route = Screen.EditMovie.route,
            arguments = listOf(
                navArgument("movieId") {type = NavType.IntType}
            )
        ) {backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: return@composable
            AddMovieScreen (
                movie = null,
                movieIdForEdit = movieId,
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveSuccess = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.Camera.route,
            arguments = listOf(
                navArgument("movieId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: return@composable
            CameraScreen(
                context = context,
                movieId = movieId,
                onPhotoTaken = { photoPath ->
                    navController.popBackStack()
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}