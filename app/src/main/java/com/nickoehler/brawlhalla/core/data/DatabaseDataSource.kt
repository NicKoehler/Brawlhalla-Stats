package com.nickoehler.brawlhalla.core.data

import androidx.lifecycle.LiveData
import com.nickoehler.brawlhalla.core.data.database.AppDatabase
import com.nickoehler.brawlhalla.core.data.database.entities.Player
import com.nickoehler.brawlhalla.core.domain.LocalDataSource

class DatabaseDataSource(
    database: AppDatabase
) : LocalDataSource {

    private val playerDao = database.playerDao()

    override suspend fun savePlayer(brawlhallaId: Int, name: String) {
        playerDao.insertPlayer(Player(brawlhallaId, name))
    }

    override suspend fun deletePlayer(brawlhallaId: Int) {
        playerDao.deletePlayer(brawlhallaId)
    }

    override suspend fun getPlayer(brawlhallaId: Int): Player {
        return playerDao.getPlayer(brawlhallaId)
    }

    override fun getAllPlayers(): LiveData<List<Player>> {
        return playerDao.getAllPlayers()
    }
}