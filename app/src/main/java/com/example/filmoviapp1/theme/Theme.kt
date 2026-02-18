package com.example.filmoviapp1.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColorScheme(
    primary = NetflixRed,
    secondary = NetflixRed,
    background = NetflixBlack,
    surface = NetflixDarkGray,
    onPrimary = NetflixWhite,
    onSecondary = NetflixWhite,
    onBackground = NetflixWhite,
    onSurface = NetflixWhite
)

private val LightColorPalette = lightColorScheme(
    primary = NetflixRed,
    secondary = NetflixRed,
    background = NetflixWhite,
    surface = NetflixDarkGray,
    onPrimary = NetflixWhite,
    onSecondary = NetflixWhite,
    onBackground = NetflixBlack,
    onSurface = NetflixBlack
)

@Composable
fun MovieTheme(darkTheme: Boolean = true, content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}