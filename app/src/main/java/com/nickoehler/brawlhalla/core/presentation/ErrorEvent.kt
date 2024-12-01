package com.nickoehler.brawlhalla.core.presentation

import com.nickoehler.brawlhalla.core.domain.util.NetworkError

sealed interface ErrorEvent {
    data class Error(val error: NetworkError) : ErrorEvent
}