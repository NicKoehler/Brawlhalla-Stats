package com.nickoehler.brawlhalla.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun LegendImage(legendName: String?, legendImage: String?) {
    if (legendName != null && legendImage != null) {
        AsyncImage(
            legendImage,
            contentDescription = legendName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(25.dp))
                .background(MaterialTheme.colorScheme.surfaceBright),
        )
    } else {
        Box(
            Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(25.dp))
                .shimmerEffect()
        )
    }
}