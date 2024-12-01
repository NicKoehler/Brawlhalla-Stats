package com.nickoehler.brawlhalla.core.presentation

import androidx.compose.runtime.Immutable

@Immutable
data class CustomAppBarState(
    val isOpenSearch: Boolean = false,
    val searchQuery: String = ""
)
