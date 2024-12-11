package com.nickoehler.brawlhalla.favorites.presentation

import androidx.compose.runtime.Immutable
import com.nickoehler.brawlhalla.core.data.database.entities.Clan
import com.nickoehler.brawlhalla.core.data.database.entities.Player
import com.nickoehler.brawlhalla.favorites.presentation.model.FavoriteType

@Immutable
data class FavoritesState(
    val players: List<Player> = emptyList(),
    val clans: List<Clan> = emptyList(),
    val selectedFavoriteType: FavoriteType? = null
)
