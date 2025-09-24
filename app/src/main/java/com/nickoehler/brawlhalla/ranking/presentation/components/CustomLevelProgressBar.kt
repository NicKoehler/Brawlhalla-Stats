package com.nickoehler.brawlhalla.ranking.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.core.presentation.components.AnimatedLinearProgressBar
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun CustomLevelProgressBar(
    percentage: Double,
    key: String,
    currentLevel: Int,
    nextLevel: Int?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (nextLevel != null) {
            Text(currentLevel.toString())
            Icon(Icons.Default.KeyboardDoubleArrowRight, null)
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            AnimatedLinearProgressBar(
                percentage,
                key,
                height = 15.dp,
                modifier = Modifier.fillMaxWidth()
            )
            if (nextLevel == null) {
                Text(currentLevel.toString(), color = MaterialTheme.colorScheme.onPrimary)
            }
        }
        if (nextLevel != null) {
            Icon(Icons.Default.KeyboardDoubleArrowRight, null)
            Text(nextLevel.toString())
        } else {
            Spacer(Modifier)
        }
    }
}


@PreviewLightDark
@Composable
private fun CustomLevelProgressBarPreview1() {
    BrawlhallaTheme {
        Surface {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(10.dp)
            ) {
                CustomLevelProgressBar(
                    percentage = 0.5,
                    key = "nic",
                    currentLevel = 99,
                    nextLevel = 100
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CustomLevelProgressBarPreview2() {
    BrawlhallaTheme {
        Surface {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(10.dp)
            ) {
                CustomLevelProgressBar(
                    percentage = 0.99,
                    key = "nic",
                    currentLevel = 100,
                    nextLevel = null
                )
            }
        }
    }
}