package com.example.recipeapp.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

enum class HeartButtonState {
    IDLE, PRESSED
}

@Composable
fun heartAnimation() {

    var heartState by remember { mutableStateOf(HeartButtonState.IDLE) }

    val idleIconSize = 50f
    val expandedIconSize = 80f

    val colorRed = Color.Red
    val colorGray = Color.LightGray

    val colorAnimate by animateColorAsState(
        targetValue = if (heartState == HeartButtonState.PRESSED) colorRed else colorGray,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing), label = ""
    )

    val heartAnimate by animateFloatAsState(
        targetValue = if (heartState == HeartButtonState.PRESSED) expandedIconSize else idleIconSize,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing), label = ""
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            contentDescription = "Heart",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clickable{
                    heartState = if (heartState == HeartButtonState.IDLE) HeartButtonState.PRESSED else HeartButtonState.IDLE
                }
                .height(heartAnimate.dp)
                .width(heartAnimate.dp),
            imageVector = Icons.Default.Favorite,
            tint = colorAnimate
        )
    }
}