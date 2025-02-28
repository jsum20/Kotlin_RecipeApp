package com.example.recipeapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = primaryDark100,
    onPrimary = Color.White,
    secondary = secondaryColour100,
    onSecondary = Color.White,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = primaryDark100,
    onBackground = black2,
    surface = Color.White,
    onSurface = black2,
)

private val LightColorScheme = lightColorScheme(
    primary = secondaryColour200,
    onPrimary = black1,
    secondary = secondaryColour200,
    onSecondary = black1,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = secondaryColour200,
    onBackground = black2,
    surface = greyColour,
    onSurface = black2,

        /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
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