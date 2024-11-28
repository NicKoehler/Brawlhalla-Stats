package com.nickoehler.brawlhalla.core.presentation.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import kotlinx.coroutines.delay

@Composable
fun AnimatedLinearProgressIndicator(
    indicatorProgress: Float,
    label: String,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    progressAnimDuration: Int = 1_500,
    height: Dp = 10.dp,
    millisDelay: Long = 0,
    modifier: Modifier = Modifier
) {
    var progress by remember {
        mutableFloatStateOf(0F)
    }

    val progressAnimation by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = progressAnimDuration,
            easing = FastOutSlowInEasing
        ),
        label = label,
    )
    LinearProgressIndicator(
        drawStopIndicator = {},
        strokeCap = StrokeCap.Butt,
        progress = { progressAnimation },
        modifier = modifier
            .height(height)
            .clip(CircleShape),
    )
    LaunchedEffect(lifecycleOwner) {
        delay(millisDelay)
        progress = indicatorProgress
    }
}

@PreviewLightDark
@Composable
private fun PreviewCircularProgressBar() {
    BrawlhallaTheme {
        Surface {
            AnimatedLinearProgressIndicator(
                0.5f,
                "test"
            )
        }
    }

}