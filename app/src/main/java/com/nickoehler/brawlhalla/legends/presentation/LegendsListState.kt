package com.nickoehler.brawlhalla.legends.presentation

import androidx.compose.runtime.Immutable
import com.nickoehler.brawlhalla.core.domain.util.NetworkError
import com.nickoehler.brawlhalla.core.presentation.models.WeaponUi
import com.nickoehler.brawlhalla.legends.domain.LegendStat
import com.nickoehler.brawlhalla.legends.presentation.models.FilterOptions
import com.nickoehler.brawlhalla.legends.presentation.models.LegendDetailUi

@Immutable
data class LegendsListState(
    val isListLoading: Boolean = false,
    val selectedLegendId: Long? = null,
    val isDetailLoading: Boolean = false,
    val legends: List<LegendDetailUi> = emptyList(),
    val weapons: List<WeaponUi> = emptyList(),
    val legendDetailUi: LegendDetailUi? = null,
    val isFilterOpen: Boolean = false,
    val selectedStatType: LegendStat = LegendStat.STRENGTH,
    val selectedStatValue: Int = 3,
    val selectedFilter: FilterOptions = FilterOptions.WEAPONS,
    val searchQuery: String = "",
    val error: NetworkError? = null
)
