package com.nickoehler.brawlhalla.legends.presentation

import com.nickoehler.brawlhalla.legends.domain.LegendStat
import com.nickoehler.brawlhalla.legends.presentation.models.FilterOptions

sealed interface LegendAction {
    data class SelectLegend(val legendId: Long) : LegendAction
    data class SelectStat(val stat: LegendStat, val value: Int) : LegendAction
    data class SelectFilter(val filter: FilterOptions) : LegendAction
    data object OnFilterToggle : LegendAction
    data class QueryChange(val query: String) : LegendAction
}
