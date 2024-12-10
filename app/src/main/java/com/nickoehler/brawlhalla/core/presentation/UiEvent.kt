package com.nickoehler.brawlhalla.core.presentation

import com.nickoehler.brawlhalla.core.domain.util.NetworkError
import com.nickoehler.brawlhalla.ranking.domain.RankingMessage

sealed interface UiEvent {
    data class Error(val error: NetworkError) : UiEvent
    data class Message(val message: RankingMessage) : UiEvent
    data object ScrollToTop : UiEvent
    data object NavigateToList : UiEvent
    data object NavigateToDetail : UiEvent
    data object PopBackToList : UiEvent
}