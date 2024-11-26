package com.nickoehler.brawlhalla.legends.presentation

sealed interface LegendAction {
    data class SelectLegend(val legendId: Int) : LegendAction
    data class SearchQuery(val text: String) : LegendAction
    data class ToggleSearch(val isOpen: Boolean) : LegendAction
    data object ToggleFilters : LegendAction
}
