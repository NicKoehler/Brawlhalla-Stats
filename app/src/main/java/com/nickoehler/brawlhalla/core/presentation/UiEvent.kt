package com.nickoehler.brawlhalla.core.presentation

import com.nickoehler.brawlhalla.core.domain.util.NetworkError

sealed interface UiEvent {
    data class Error(val error: NetworkError) : UiEvent
    data object ScrollToTop : UiEvent
    data object NavigateToList : UiEvent
    data object NavigateToDetail : UiEvent
}