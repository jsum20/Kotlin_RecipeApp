package com.example.recipeapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = primaryDark100,
    onPrimary = Color.White,
    secondary = primaryDark200,
    onSecondary = Color.White,
    error = redErrorDark,
    onError = Color.White,
    background = primaryDark100,
    onBackground = Color.White,
    surface = primaryDark200,
    onSurface = Color.White,
)

private val LightColorScheme = lightColorScheme(
    primary = primaryColour100,
    onPrimary = black1,
    secondary = primaryColour200,
    onSecondary = black1,
    error = redErrorLight,
    onError = Color.White,
    background = primaryColour100,
    onBackground = black2,
    surface = greyColour,
    onSurface = black2,
)

@Composable
fun RecipeAppTheme(
        // Dynamic color is available on Android 12+
        content: @Composable () -> Unit
) {
    MaterialTheme(
            colorScheme = LightColorScheme,
            typography = Typography,
            content = content
    )
}