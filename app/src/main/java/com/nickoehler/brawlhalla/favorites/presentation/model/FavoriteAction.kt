package com.nickoehler.brawlhalla.favorites.presentation.model

import com.nickoehler.brawlhalla.core.data.database.entities.Clan
import com.nickoehler.brawlhalla.core.data.database.entities.Player

sealed interface FavoriteAction {
    data class PlayerClicked(val brawlhallaId: Long) : FavoriteAction
    data class ClanClicked(val clanId: Long) : FavoriteAction
    data class SelectFavorite(val fav: FavoriteType) : FavoriteAction
    data class RestorePlayer(val player: Player) : FavoriteAction
    data class RestoreClan(val clan: Clan) : FavoriteAction
    data class DeletePlayer(val brawlhallaId: Long) : FavoriteAction
    data class DeleteClan(val clanId: Long) : FavoriteAction
    data class PlayerDragged(val fromIndex: Int, val toIndex: Int) : FavoriteAction
    data class ClanDragged(val fromIndex: Int, val toIndex: Int) : FavoriteAction
    data object PersistPlayers : FavoriteAction
    data object PersistClans : FavoriteAction
}