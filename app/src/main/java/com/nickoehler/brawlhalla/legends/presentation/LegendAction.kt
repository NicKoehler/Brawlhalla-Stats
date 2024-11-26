package com.nickoehler.brawlhalla.legends.presentation

import com.nickoehler.brawlhalla.legends.domain.Stat
import com.nickoehler.brawlhalla.legends.presentation.models.FilterOptions

sealed interface LegendAction {
    data class SelectLegend(val legendId: Int) : LegendAction
    data class SearchQuery(val text: String) : LegendAction
    data class ToggleSearch(val isOpen: Boolean) : LegendAction
    data class SelectStat(val stat: Stat) : LegendAction
    data class SlideStat(val value: Int) : LegendAction
    data class SelectFilter(val filter: FilterOptions) : LegendAction
    data object ToggleFilters : LegendAction
}
