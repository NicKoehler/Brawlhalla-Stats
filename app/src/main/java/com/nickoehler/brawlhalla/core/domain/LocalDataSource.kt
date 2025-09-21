package com.nickoehler.brawlhalla.core.domain

import com.nickoehler.brawlhalla.core.data.database.entities.Clan
import com.nickoehler.brawlhalla.core.data.database.entities.Player
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun savePlayer(player: Player)

    suspend fun deletePlayer(brawlhallaId: Long)

    fun getPlayer(brawlhallaId: Long): Flow<Player?>

    fun getAllPlayers(): Flow<List<Player>>

    suspend fun saveClan(clan: Clan)

    suspend fun deleteClan(clanId: Long)

    fun getClan(clanId: Long): Flow<Clan?>

    fun getAllClans(): Flow<List<Clan>>

    suspend fun updatePlayers(players: List<Player>)

    suspend fun updateClans(clans: List<Clan>)

}