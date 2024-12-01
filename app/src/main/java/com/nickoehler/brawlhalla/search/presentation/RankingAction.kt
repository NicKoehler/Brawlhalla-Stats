package com.nickoehler.brawlhalla.search.presentation

sealed interface RankingAction {
    data object LoadMore : RankingAction
}