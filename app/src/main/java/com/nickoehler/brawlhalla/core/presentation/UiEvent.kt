package com.nickoehler.brawlhalla.core.presentation

import com.nickoehler.brawlhalla.ranking.domain.RankingMessage

sealed interface UiEvent {
    data class Message(val message: RankingMessage) : UiEvent
    data class GoToDetail(val id: Long) : UiEvent
    data object ScrollToTop : UiEvent
}