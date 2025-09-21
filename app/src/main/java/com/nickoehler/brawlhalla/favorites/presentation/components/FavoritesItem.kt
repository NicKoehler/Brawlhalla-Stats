package com.nickoehler.brawlhalla.favorites.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DragHandle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.core.presentation.components.DraggableListState
import com.nickoehler.brawlhalla.core.presentation.components.dragHandle
import com.nickoehler.brawlhalla.core.presentation.components.rememberDraggableListState
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import com.nickoehler.brawlhalla.ui.theme.LoseColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun FavoritesItem(
    itemId: Long,
    itemName: String,
    leadingIcon: ImageVector,
    coroutineScope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    dismissState: SwipeToDismissBoxState,
    draggableState: DraggableListState,
    onSwipeAction: () -> Unit,
    onActionPerformed: () -> Unit,
    onClickAction: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val context = LocalContext.current
    val color by animateColorAsState(
        targetValue = when (dismissState.dismissDirection) {
            SwipeToDismissBoxValue.Settled -> MaterialTheme.colorScheme.surfaceContainerHighest
            SwipeToDismissBoxValue.StartToEnd -> LoseColor
            SwipeToDismissBoxValue.EndToStart -> LoseColor
        },
        animationSpec = tween(500),
        label = "animatedColor:$itemName",
    )
    SwipeToDismissBox(
        state = dismissState,
        onDismiss = { direction ->
            coroutineScope.launch {
                onSwipeAction()
                val result = snackBarHostState.showSnackbar(
                    message = context.getString(R.string.deleted),
                    actionLabel = context.getString(R.string.cancel),
                    duration = SnackbarDuration.Short
                )

                when (result) {
                    SnackbarResult.Dismissed -> Unit
                    SnackbarResult.ActionPerformed -> {
                        dismissState.reset()
                        onActionPerformed()
                    }
                }
            }
        },
        enableDismissFromEndToStart = false,
        backgroundContent = {
            Box(
                Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(color),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Spacer(Modifier.width(24.dp))
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
            Text(itemName, Modifier.weight(1f))
            Icon(
                Icons.Default.DragHandle,
                null,
                modifier = Modifier.dragHandle(
                    key = itemId,
                    state = draggableState,
                ),

                )
        }
    }
}

@PreviewLightDark
@Composable
private fun FavoritesItemPreview() {

    BrawlhallaTheme {
        Surface {
            FavoritesItem(
                1,
                "ops",
                Icons.Default.Person,
                rememberCoroutineScope(),
                SnackbarHostState(),
                rememberSwipeToDismissBoxState(),
                rememberDraggableListState(
                    onMove = { _, _ -> },
                    onMoveCompleted = {}
                ),
                {},
                {},
                {}
            )
        }
    }

}