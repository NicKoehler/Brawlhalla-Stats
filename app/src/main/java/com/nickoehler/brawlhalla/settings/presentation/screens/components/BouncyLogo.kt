package com.nickoehler.brawlhalla.settings.presentation.screens.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.R

private enum class BounceState { Pressed, Released }


@Composable
fun BouncyLogo() {
    var currentState: BounceState by remember { mutableStateOf(BounceState.Released) }
    val transition = updateTransition(targetState = currentState, label = "logo animation")

    val scale: Float by transition.animateFloat(
        transitionSpec = { spring(stiffness = 900f) }, label = "logo animation"
    ) { state ->
        if (state == BounceState.Pressed) {
            0.8f
        } else {
            1f
        }
    }

    val colors = listOf(
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.secondaryContainer,
        MaterialTheme.colorScheme.tertiaryContainer,
    )

    var colorIndex by remember { mutableIntStateOf(0) }

    val animatedColor by animateColorAsState(
        if (currentState == BounceState.Pressed) {
            colors[(colorIndex + 1) % colors.size]
        } else {
            colors[colorIndex]
        },
        label = "color"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        animatedColor,
                        animatedColor.copy(alpha = 0f),
                    ),
                )
            )
            .padding(60.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures(onPress = {
                        currentState = BounceState.Pressed
                        tryAwaitRelease()
                        currentState = BounceState.Released
                        colorIndex = (colorIndex + 1) % colors.size
                    })
                }
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .size(200.dp)
        )
    }
}