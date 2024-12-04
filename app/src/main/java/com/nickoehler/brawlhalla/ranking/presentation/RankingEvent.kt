package com.nickoehler.brawlhalla.ranking.presentation

sealed interface RankingEvent {
    data object NavigateToDetail : RankingEvent
    data object NavigateToList : RankingEvent
}