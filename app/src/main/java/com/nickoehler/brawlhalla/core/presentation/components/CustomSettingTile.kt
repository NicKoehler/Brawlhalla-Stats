package com.nickoehler.brawlhalla.core.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeveloperMode
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

data class SettingTile(
    val title: String,
    val description: String? = null,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@Composable
fun CustomSettingTile(
    items: List<SettingTile>,
    modifier: Modifier = Modifier,
    outerRounding: Dp = 24.dp,
    innerRounding: Dp = 4.dp
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items.forEachIndexed { index, item ->

            val animatedFloat = remember { Animatable(0f) }

            LaunchedEffect(item) {
                animatedFloat.animateTo(360f)
                animatedFloat.snapTo(0f)
            }

            ListItem(
                headlineContent = {
                    Text(item.title)
                },
                supportingContent = {
                    if (item.description != null) {
                        Text(item.description)
                    } else null
                },
                leadingContent = {
                    AnimatedContent(
                        targetState = item,
                        transitionSpec = { fadeIn().togetherWith(fadeOut()) },
                        modifier = Modifier.rotate(animatedFloat.value)
                    ) {
                        Icon(it.icon, item.title)
                    }
                },
                colors = ListItemDefaults.colors().copy(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ),
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            bottomEnd = if (index == items.size - 1) outerRounding else innerRounding,
                            bottomStart = if (index == items.size - 1) outerRounding else innerRounding,
                            topEnd = if (index == 0) outerRounding else innerRounding,
                            topStart = if (index == 0) outerRounding else innerRounding,
                        )
                    )
                    .clickable {
                        item.onClick()
                    }
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun CustomSettingTilePreview() {
    BrawlhallaTheme {
        Surface {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CustomSettingTile(
                    (0..5).map {
                        SettingTile(
                            title = "Setting name",
                            description = "Awesome setting description",
                            icon = Icons.Default.DeveloperMode,
                            onClick = {}
                        )
                    }
                )
            }
        }
    }
}