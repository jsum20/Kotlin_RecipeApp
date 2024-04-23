package com.example.recipeapp.presentation.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp

@Composable
fun PulseAnimation() {

    val PULSE_RADIUS_MIN = 40f
    val PULSE_RADIUS_MAX = 50f
    val PULSE_DURATION = 500

    val color = MaterialTheme.colorScheme.primary

    var pulseAnimate by remember { mutableStateOf(false) }

    val pulseFunction by animateFloatAsState(
        targetValue = if (pulseAnimate) PULSE_RADIUS_MAX else PULSE_RADIUS_MIN,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = PULSE_DURATION,
                easing = FastOutSlowInEasing
            ),
        ), label = ""
    )

    // Heart Animation
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            contentDescription = "Heart",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .height(pulseFunction.dp)
                .width(pulseFunction.dp),
            imageVector = Icons.Default.Favorite,
        )
    }

    // Use `pulseFunction` in your composable to apply the animated radius
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .clickable {
                pulseAnimate = !pulseAnimate
            }
    ) {
        drawCircle(
            radius = pulseFunction,
            center = center,
            brush = SolidColor(color)
        )
    }
}
