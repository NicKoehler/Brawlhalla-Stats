package com.nickoehler.brawlhalla.core.domain

import com.nickoehler.brawlhalla.core.data.database.entities.Guild
import com.nickoehler.brawlhalla.core.data.database.entities.Player
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun savePlayer(player: Player)

    suspend fun deletePlayer(brawlhallaId: Long)

    fun getPlayer(brawlhallaId: Long): Flow<Player?>

    fun getAllPlayers(): Flow<List<Player>>

    suspend fun saveGuild(guild: Guild)

    suspend fun deleteGuild(guildId: Long)

    fun getGuild(guildId: Long): Flow<Guild?>

    fun getAllGuilds(): Flow<List<Guild>>

    suspend fun updatePlayers(players: List<Player>)

    suspend fun updateGuilds(guilds: List<Guild>)

}