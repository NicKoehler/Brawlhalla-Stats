package com.nickoehler.brawlhalla.core.presentation

sealed interface AppBarAction {
    data object OpenSearch : AppBarAction
    data object CloseSearch : AppBarAction
    data object Search : AppBarAction
    data object OpenInfo : AppBarAction
    data class QueryChange(val query: String) : AppBarAction
}