package org.cloud99p.maroon.view.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun AnimatedTotal(
    value: Float,
    modifier: Modifier = Modifier
) {
    val animatedTotal by animateFloatAsState(
        targetValue = value,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearEasing
        ),
        label = "total_animated"
    )

    Text(
        text = "${animatedTotal.roundToInt() * 123}",
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        modifier = modifier
    )
}
