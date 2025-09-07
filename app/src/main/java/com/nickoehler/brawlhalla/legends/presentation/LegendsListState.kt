package com.nickoehler.brawlhalla.legends.presentation

import androidx.compose.runtime.Immutable
import com.nickoehler.brawlhalla.core.presentation.models.WeaponUi
import com.nickoehler.brawlhalla.legends.domain.LegendStat
import com.nickoehler.brawlhalla.legends.presentation.models.FilterOptions
import com.nickoehler.brawlhalla.legends.presentation.models.LegendDetailUi
import com.nickoehler.brawlhalla.legends.presentation.models.LegendUi

@Immutable
data class LegendsListState(
    val isListLoading: Boolean = false,
    val isDetailLoading: Boolean = false,
    val legends: List<LegendUi> = emptyList(),
    val weapons: List<WeaponUi> = emptyList(),
    val selectedLegendUi: LegendDetailUi? = null,
    val isFilterOpen: Boolean = false,
    val selectedStatType: LegendStat = LegendStat.STRENGTH,
    val selectedStatValue: Int = 3,
    val selectedFilter: FilterOptions = FilterOptions.WEAPONS,
    val searchQuery: String = ""
)
