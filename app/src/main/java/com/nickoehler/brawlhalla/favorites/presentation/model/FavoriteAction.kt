package com.nickoehler.brawlhalla.favorites.presentation.model

import com.nickoehler.brawlhalla.core.data.database.entities.Guild
import com.nickoehler.brawlhalla.core.data.database.entities.Player

sealed interface FavoriteAction {
    data class PlayerClicked(val brawlhallaId: Long) : FavoriteAction
    data class GuildClicked(val guildId: Long) : FavoriteAction
    data class SelectFavorite(val fav: FavoriteType) : FavoriteAction
    data class RestorePlayer(val player: Player) : FavoriteAction
    data class RestoreGuild(val guild: Guild) : FavoriteAction
    data class DeletePlayer(val brawlhallaId: Long) : FavoriteAction
    data class DeleteGuild(val guildId: Long) : FavoriteAction
    data class PlayerDragged(val fromIndex: Int, val toIndex: Int) : FavoriteAction
    data class GuildDragged(val fromIndex: Int, val toIndex: Int) : FavoriteAction
    data object PersistPlayers : FavoriteAction
    data object PersistGuilds : FavoriteAction
}