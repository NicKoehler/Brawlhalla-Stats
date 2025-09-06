package com.nickoehler.brawlhalla.legends.presentation.components

import androidx.compose.runtime.Composable
import com.nickoehler.brawlhalla.legends.domain.LegendStat
import com.nickoehler.brawlhalla.legends.presentation.LegendDetailAction
import com.nickoehler.brawlhalla.legends.presentation.models.LegendDetailUi
import com.nickoehler.brawlhalla.legends.presentation.models.getStat

@Composable
fun LegendDetailStats(
    isLoading: Boolean,
    legend: LegendDetailUi?,
    onLegendAction: (LegendDetailAction) -> Unit
) {
    if (isLoading || legend != null) {
        LegendStat.entries.forEachIndexed { index, stat ->
            LegendStatItem(
                stat,
                legend?.getStat(stat),
                onLegendAction,
                delayMillis = 100 * index
            )
        }
    }
}