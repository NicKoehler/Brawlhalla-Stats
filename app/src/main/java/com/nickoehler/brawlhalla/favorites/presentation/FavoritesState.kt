package com.nickoehler.brawlhalla.favorites.presentation

import androidx.compose.runtime.Immutable
import com.nickoehler.brawlhalla.core.data.database.entities.Player

@Immutable
data class FavoritesState(
    val players: List<Player> = emptyList()
)
