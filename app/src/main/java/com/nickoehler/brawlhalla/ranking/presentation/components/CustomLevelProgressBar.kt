package com.nickoehler.brawlhalla.ranking.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.core.presentation.components.AnimatedLinearProgressBar

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
        Text(currentLevel.toString())
        Icon(Icons.Default.KeyboardDoubleArrowRight, null)
        AnimatedLinearProgressBar(
            percentage,
            key,
            height = 15.dp,
            modifier = Modifier.weight(1f)
        )
        if (nextLevel != null) {
            Icon(Icons.Default.KeyboardDoubleArrowRight, null)
            Text(nextLevel.toString())
        } else {
            Spacer(Modifier)
        }
    }
}