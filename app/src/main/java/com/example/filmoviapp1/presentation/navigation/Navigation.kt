package com.example.filmoviapp1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
                onAddClick = { navController.navigate(Screen.AddMovie.route) },
                onNavigateToDetail = { movie ->
                    navController.navigate(Screen.MovieDetail.createRoute(movie.id))
                }
            )
        }

        composable(Screen.AddMovie.route) {
            AddMovieScreen(
                navController = navController,
                movieIdForEdit = null,
                onBackClick = { navController.popBackStack() },
                onSaveSuccess = { navController.popBackStack() },
                onPhotoClick = {
                    navController.navigate(Screen.Camera.createRoute(-1)) // -1 za novi movie
                }
            )
        }

        composable(
            route = Screen.EditMovie.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: return@composable
            AddMovieScreen(
                navController = navController,
                movieIdForEdit = movieId,
                onBackClick = { navController.popBackStack() },
                onSaveSuccess = { navController.popBackStack() },
                onPhotoClick = {
                    navController.navigate(Screen.Camera.createRoute(movieId))
                }
            )
        }

        composable(
            route = Screen.MovieDetail.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId")!!
            MovieDetailScreen(
                movieId = movieId,
                onBackClick = { navController.popBackStack() },
                onEditClick = { movie ->
                    navController.navigate(Screen.EditMovie.createRoute(movie.id))
                },
                onPhotoClick = {
                    navController.navigate(Screen.Camera.createRoute(movieId))
                }
            )
        }

        composable(
            route = Screen.Camera.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: -1

            CameraScreen(
                context = context,
                movieId = movieId,
                onPhotoTaken = { photoPath ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("photoPath", photoPath)

                    navController.popBackStack()
                },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}