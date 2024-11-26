package com.nickoehler.brawlhalla.legends.presentation

import androidx.compose.runtime.Immutable
import com.nickoehler.brawlhalla.core.presentation.domain.WeaponUi
import com.nickoehler.brawlhalla.legends.presentation.models.LegendDetailUi
import com.nickoehler.brawlhalla.legends.presentation.models.LegendUi

@Immutable
data class LegendsListState(
    val isListLoading: Boolean = false,
    val isDetailLoading: Boolean = false,
    val legends: List<LegendUi> = emptyList(),
    val weapons: List<WeaponUi> = emptyList(),
    val selectedLegendUi: LegendDetailUi? = null,
    val openSearch: Boolean = false,
    val openFilters: Boolean = false,
    val searchQuery: String = "",
)
