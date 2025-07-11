package org.cloud99p.nextbox.view.component

import androidx.compose.animation.core.LinearOutSlowInEasing
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
fun AnimatedFloat(
    value: Float,
    modifier: Modifier = Modifier
) {
    val animatedTotal by animateFloatAsState(
        targetValue = value,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearOutSlowInEasing
        ),
        label = "total_animated"
    )

    Text(
        text = "${animatedTotal.roundToInt()}",
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        modifier = modifier
    )
}
