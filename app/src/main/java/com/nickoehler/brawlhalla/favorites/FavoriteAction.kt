package com.nickoehler.brawlhalla.favorites

sealed interface FavoriteAction {
    data class SelectPlayer(val brawlhallaId: Int) : FavoriteAction
    data class SelectClan(val clanId: Int) : FavoriteAction
    data class ToggleFavorites(val brawlhallaId: Int, val name: String) : FavoriteAction
}