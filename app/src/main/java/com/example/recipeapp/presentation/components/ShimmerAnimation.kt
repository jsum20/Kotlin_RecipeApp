package com.example.recipeapp.presentation.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.delay

enum class AnimationState {
    START, END
}

@Composable
fun ShimmerAnimation(
    widthPx: Float,
    heightPx: Float,
    gradientWidth: Float
): Pair<Offset, Offset> {

    val transition = rememberInfiniteTransition(label = "animation");

    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = widthPx + gradientWidth,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1300,
                delayMillis = 300,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ), label = "Shimmer loading animation"
    )

    val startOffset = Offset(x = translateAnimation.value - widthPx, y = 0f)
    val endOffset = Offset(x = translateAnimation.value, y = heightPx)

    return Pair(startOffset, endOffset)
}