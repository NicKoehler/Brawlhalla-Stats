package com.nickoehler.brawlhalla.core.domain

import com.nickoehler.brawlhalla.core.data.database.entities.Clan
import com.nickoehler.brawlhalla.core.data.database.entities.Player
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun savePlayer(brawlhallaId: Int, name: String)

    suspend fun deletePlayer(brawlhallaId: Int)

    suspend fun getPlayer(brawlhallaId: Int): Player?

    fun getAllPlayers(): Flow<List<Player>>

    suspend fun saveClan(clanId: Int, name: String)

    suspend fun deleteClan(clanId: Int)

    suspend fun getClan(clanId: Int): Clan?

    fun getAllClans(): Flow<List<Clan>>
}