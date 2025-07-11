package org.cloud99p.nextbox.view.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun AnimatedCounter(
    value: Int,
    modifier: Modifier = Modifier
) = AnimatedContent(
    targetState = value,
    transitionSpec = {
        slideInVertically { it } togetherWith slideOutVertically { -it }
    },
    modifier = modifier
) {
    Text(
        text = it.toString(),
        textAlign = TextAlign.Center,
        fontSize = 50.sp
    )
}
