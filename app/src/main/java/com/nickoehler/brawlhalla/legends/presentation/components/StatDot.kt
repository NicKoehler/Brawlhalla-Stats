package com.nickoehler.brawlhalla.legends.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun StatDot(color: Color) {
    Box(
        Modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(color)
    )
}


@PreviewLightDark
@Composable
private fun StatChipPreview() {
    BrawlhallaTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            repeat(10) {
                StatDot(MaterialTheme.colorScheme.primary)
            }
        }
    }
}
