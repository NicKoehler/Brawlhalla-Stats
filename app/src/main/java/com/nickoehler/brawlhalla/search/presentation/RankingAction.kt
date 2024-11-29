package com.nickoehler.brawlhalla.search.presentation

sealed interface RankingAction {
    data object LoadMore : RankingAction
    data object Search : RankingAction
    data object ResetSearch : RankingAction
    data class SearchQuery(val query: String) : RankingAction
}