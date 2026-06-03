package com.nickoehler.brawlhalla.favorites.presentation.model

import androidx.compose.runtime.Immutable
import com.nickoehler.brawlhalla.core.data.database.entities.Guild
import com.nickoehler.brawlhalla.core.data.database.entities.Player

@Immutable
data class FavoritesState(
    val players: List<Player> = emptyList(),
    val guilds: List<Guild> = emptyList(),
    val selectedFavoriteType: FavoriteType? = null
)