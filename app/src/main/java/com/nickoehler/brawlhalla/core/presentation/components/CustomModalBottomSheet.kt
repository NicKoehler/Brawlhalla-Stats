package com.nickoehler.brawlhalla.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme


data class ModalBottomSheetItem<T>(
    val value: T,
    val stringValue: String,
    val icon: ImageVector? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> CustomModalBottomSheet(
    modifier: Modifier = Modifier,
    currentSettingValue: T,
    items: List<ModalBottomSheetItem<T>>,
    onDismissRequest: () -> Unit,
    onItemClick: (T) -> Unit
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest
    ) {
        Box(
            Modifier
                .clip(RoundedCornerShape(25.dp))
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items.forEach { item ->
                    Row(
                        Modifier
                            .clip(CircleShape)
                            .background(
                                if (currentSettingValue == item.value) {
                                    MaterialTheme.colorScheme.primaryContainer
                                } else {
                                    MaterialTheme.colorScheme.surfaceContainer
                                }
                            )
                            .selectable(
                                selected = currentSettingValue == item,
                                onClick = {
                                    onItemClick(item.value)
                                },
                            )
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AnimatedVisibility(
                            currentSettingValue == item.value,
                            enter = expandHorizontally() + fadeIn(),
                            exit = shrinkHorizontally() + fadeOut(),
                        ) {
                            Icon(Icons.Default.Done, null)
                        }
                        Spacer(modifier.width(10.dp))
                        if (item.icon != null) {
                            Icon(item.icon, item.stringValue)
                            Spacer(modifier.width(10.dp))
                        }
                        Text(item.stringValue, modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CustomModalBottomSheetPreview() {
    BrawlhallaTheme {
        Surface {
            Box(contentAlignment = Alignment.Center) {
                CustomModalBottomSheet(
                    currentSettingValue = 1,
                    items = (0..5).map {
                        ModalBottomSheetItem(
                            value = it,
                            stringValue = it.toString(),
                            icon = Icons.Default.WbSunny
                        )
                    },
                    onDismissRequest = {},
                    onItemClick = {},
                )
            }
        }
    }
}