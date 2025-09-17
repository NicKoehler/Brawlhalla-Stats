package com.nickoehler.brawlhalla.favorites

import com.nickoehler.brawlhalla.core.data.database.entities.Clan
import com.nickoehler.brawlhalla.core.data.database.entities.Player
import com.nickoehler.brawlhalla.favorites.presentation.model.FavoriteType

sealed interface FavoriteAction {
    data class PlayerClicked(val brawlhallaId: Long) : FavoriteAction
    data class ClanClicked(val clanId: Long) : FavoriteAction
    data class SelectFavorite(val fav: FavoriteType) : FavoriteAction
    data class RestorePlayer(val player: Player) : FavoriteAction
    data class RestoreClan(val clan: Clan) : FavoriteAction
    data class DeletePlayer(val brawlhallaId: Long) : FavoriteAction
    data class DeleteClan(val clanId: Long) : FavoriteAction
}