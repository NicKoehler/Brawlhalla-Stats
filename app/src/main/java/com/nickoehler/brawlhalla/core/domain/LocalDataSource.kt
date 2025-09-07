package com.nickoehler.brawlhalla.core.domain

import com.nickoehler.brawlhalla.core.data.database.entities.Clan
import com.nickoehler.brawlhalla.core.data.database.entities.Player
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun savePlayer(brawlhallaId: Long, name: String)

    suspend fun deletePlayer(brawlhallaId: Long)

    fun getPlayer(brawlhallaId: Long): Flow<Player?>

    fun getAllPlayers(): Flow<List<Player>>

    suspend fun saveClan(clanId: Long, name: String)

    suspend fun deleteClan(clanId: Long)

    fun getClan(clanId: Long): Flow<Clan?>

    fun getAllClans(): Flow<List<Clan>>
}