package com.nickoehler.brawlhalla.core.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.launch

@Composable
fun CustomFloatingActionButton(
    scrollState: LazyListState,
) {
    val coroutineScope = rememberCoroutineScope()
    var visibleIndexIsNotZero by remember { mutableStateOf(false) }

    val isVisible by remember {
        derivedStateOf {
            scrollState.lastScrolledBackward && visibleIndexIsNotZero
        }
    }

    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.firstVisibleItemIndex }
            .collect {
                visibleIndexIsNotZero = it != 0
            }
    }
    val translationY by animateFloatAsState(if (isVisible) 0f else 500f)

    FloatingActionButton(
        modifier = Modifier.graphicsLayer(
            translationY = translationY
        ),
        onClick = {
            coroutineScope.launch {
                scrollState.animateScrollToItem(0)
            }
        },
    ) {
        Icon(Icons.Default.ArrowUpward, null)
    }

}
