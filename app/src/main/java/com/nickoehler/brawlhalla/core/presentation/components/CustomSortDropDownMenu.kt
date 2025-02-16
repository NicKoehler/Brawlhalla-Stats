package com.nickoehler.brawlhalla.core.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Mode
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun CustomSortDropDownMenu(
    modifier: Modifier = Modifier,
    reversed: Boolean,
    expanded: Boolean,
    icon: ImageVector,
    onSortClick: () -> Unit,
    onReversedClick: () -> Unit,
    selected: @Composable () -> Unit,
    content: @Composable (ColumnScope.() -> Unit),
) {
    val rotation by animateFloatAsState(if (reversed) 180f else 0f)

    Box(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onSortClick() }
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(icon, null)
                selected()
            }
            IconButton(
                onReversedClick,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceContainer)
            ) {
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    null,
                    modifier = Modifier
                        .rotate(rotation)
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onSortClick() }
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun CustomSortDropDownMenuPreview() {
    BrawlhallaTheme {
        Surface {
            CustomSortDropDownMenu(
                reversed = true,
                expanded = true,
                icon = Icons.Default.Mode,
                selected = {
                    Text("test")
                },
                onSortClick = {},
                onReversedClick = {},
            ) {
                Text("test")
            }
        }
    }
}