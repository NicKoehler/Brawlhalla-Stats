package com.nickoehler.brawlhalla.core.domain

import androidx.lifecycle.LiveData
import com.nickoehler.brawlhalla.core.data.database.entities.Clan
import com.nickoehler.brawlhalla.core.data.database.entities.Player

interface LocalDataSource {
    suspend fun savePlayer(brawlhallaId: Int, name: String)

    suspend fun deletePlayer(brawlhallaId: Int)

    suspend fun getPlayer(brawlhallaId: Int): Player?

    fun getAllPlayers(): LiveData<List<Player>>

    suspend fun saveClan(clanId: Int, name: String)

    suspend fun deleteClan(clanId: Int)

    suspend fun getClan(clanId: Int): Clan?

    fun getAllClans(): LiveData<List<Clan>>
}