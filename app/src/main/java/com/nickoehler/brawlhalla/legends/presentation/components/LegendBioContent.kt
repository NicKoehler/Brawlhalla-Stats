package com.nickoehler.brawlhalla.legends.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.core.presentation.components.shimmerEffect
import com.nickoehler.brawlhalla.legends.presentation.models.LegendDetailUi

@Composable
fun LegendBioContent(legend: LegendDetailUi?, isLoading: Boolean) {
    if (isLoading) {
        repeat(20) {
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .padding(20.dp, 5.dp)
                    .clip(CircleShape)
                    .fillMaxSize()
                    .shimmerEffect()
            )
        }
    } else if (legend != null) {
        Text(
            legend.bioText,
            modifier = Modifier
                .padding(20.dp, 0.dp)
                .verticalScroll(rememberScrollState())
        )
    }
}