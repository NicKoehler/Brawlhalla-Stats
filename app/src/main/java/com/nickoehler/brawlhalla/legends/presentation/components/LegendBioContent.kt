package com.nickoehler.brawlhalla.legends.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.legends.presentation.models.LegendDetailUi

@Composable
fun LegendBioContent(legend: LegendDetailUi?) {
    if (legend != null) {
        Text(
            legend.bioText,
            modifier = Modifier
                .padding(20.dp, 0.dp)
                .verticalScroll(rememberScrollState())
        )
    }
}