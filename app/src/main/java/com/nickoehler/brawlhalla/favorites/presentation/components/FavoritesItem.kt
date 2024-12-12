package com.nickoehler.brawlhalla.favorites.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import com.nickoehler.brawlhalla.ui.theme.LoseColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun FavoritesItem(
    coroutineScope: CoroutineScope,
    item: String,
    deleteTitle: String,
    deleteDescription: String,
    leadingIcon: ImageVector,
    onSwipeConfirmAction: () -> Unit = {},
    onClickAction: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    var isDialogOpen by remember { mutableStateOf(false) }

    val threshold: Float = with(LocalConfiguration.current) {
        (this.screenWidthDp * .8f).dp.value
    }
    val dismissState = rememberSwipeToDismissBoxState(
        positionalThreshold = { threshold }
    )

    LaunchedEffect(dismissState.targetValue) {
        if (dismissState.targetValue == SwipeToDismissBoxValue.StartToEnd) {
            isDialogOpen = true
        }
    }

    CustomDialogBox(
        deleteTitle,
        deleteDescription,
        isDialogOpen,
        onDismissRequest = {
            isDialogOpen = false
            coroutineScope.launch {
                dismissState.reset()
            }
        },
        onAcceptRequest = {
            isDialogOpen = false
            onSwipeConfirmAction()
        }
    )

    SwipeToDismissBox(
        dismissState,
        enableDismissFromEndToStart = false,
        backgroundContent = {
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.Settled -> MaterialTheme.colorScheme.surfaceBright
                    SwipeToDismissBoxValue.StartToEnd -> LoseColor
                    SwipeToDismissBoxValue.EndToStart -> LoseColor
                },
                label = "animatedColor:$item",
            )

            Box(
                Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(color),
                contentAlignment = Alignment.Center
            ) {
                Row(horizontalArrangement = Arrangement.Start) {
                    Icon(Icons.Default.Delete, null)
                }
            }
        }
    ) {
        CustomCard(
            modifier = modifier,
            onClick = onClickAction,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(leadingIcon, null)
            Text(item, Modifier.weight(1f))
        }
    }
}

@PreviewLightDark
@Composable
private fun FavoritesItemPreview() {

    BrawlhallaTheme {
        Surface {
            FavoritesItem(
                rememberCoroutineScope(),
                "ciao",
                "nice",
                "cock",
                Icons.Default.Person
            )
        }
    }

}