package com.nickoehler.brawlhalla.ranking.presentation

sealed interface RankingAction {
    data object LoadMore : RankingAction
    data class SelectRanking(val id: Int) : RankingAction
}