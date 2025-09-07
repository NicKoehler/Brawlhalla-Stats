package com.nickoehler.brawlhalla.favorites

import com.nickoehler.brawlhalla.favorites.presentation.model.FavoriteType

sealed interface FavoriteAction {
    data class SelectPlayer(val brawlhallaId: Long) : FavoriteAction
    data class SelectClan(val clanId: Long) : FavoriteAction
    data class SelectFavorite(val fav: FavoriteType) : FavoriteAction
    data class DeletePlayer(val brawlhallaId: Long) : FavoriteAction
    data class DeleteClan(val clanId: Long) : FavoriteAction
}