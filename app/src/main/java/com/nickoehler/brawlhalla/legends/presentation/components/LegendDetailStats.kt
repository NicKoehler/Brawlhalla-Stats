package com.nickoehler.brawlhalla.legends.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import com.nickoehler.brawlhalla.legends.domain.LegendStat
import com.nickoehler.brawlhalla.legends.presentation.LegendDetailAction
import com.nickoehler.brawlhalla.legends.presentation.models.LegendDetailUi
import com.nickoehler.brawlhalla.legends.presentation.models.getStat
import kotlinx.coroutines.delay

@Composable
fun LegendDetailStats(
    isLoading: Boolean,
    legend: LegendDetailUi?,
    onLegendAction: (LegendDetailAction) -> Unit
) {
    if (isLoading || legend != null) {
        LegendStat.entries.forEachIndexed { index, stat ->

            var visible by remember { mutableStateOf(false) }
            val animatedFloat by animateFloatAsState(if (visible) 1f else 0f)
            LaunchedEffect(Unit) {
                delay(100L * index)
                visible = true
            }

            LegendStatItem(
                stat = stat,
                statValue = legend?.getStat(stat),
                onLegendAction = onLegendAction,
                delayMillis = 100 * index,
                modifier = Modifier
                    .scale(animatedFloat)
                    .alpha(animatedFloat)
            )
        }
    }
}