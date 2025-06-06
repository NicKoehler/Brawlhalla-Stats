package com.nickoehler.brawlhalla.favorites

import com.nickoehler.brawlhalla.favorites.presentation.model.FavoriteType

sealed interface FavoriteAction {
    data class SelectPlayer(val brawlhallaId: Int) : FavoriteAction
    data class SelectClan(val clanId: Int) : FavoriteAction
    data class SelectFavorite(val fav: FavoriteType) : FavoriteAction
    data class DeletePlayer(val brawlhallaId: Int) : FavoriteAction
    data class DeleteClan(val clanId: Int) : FavoriteAction
}