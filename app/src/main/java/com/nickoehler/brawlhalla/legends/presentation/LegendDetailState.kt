package com.nickoehler.brawlhalla.legends.presentation

import androidx.compose.runtime.Immutable
import com.nickoehler.brawlhalla.legends.presentation.models.LegendDetailUi

@Immutable
data class LegendDetailState(
    val isDetailLoading: Boolean = false,
    val selectedLegendUi: LegendDetailUi? = null,
)
