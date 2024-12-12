package com.nickoehler.brawlhalla.core.data

import com.nickoehler.brawlhalla.core.data.database.AppDatabase
import com.nickoehler.brawlhalla.core.data.database.entities.Clan
import com.nickoehler.brawlhalla.core.data.database.entities.Player
import com.nickoehler.brawlhalla.core.domain.LocalDataSource
import kotlinx.coroutines.flow.Flow

class DatabaseDataSource(
    database: AppDatabase
) : LocalDataSource {

    private val playerDao = database.playerDao()
    private val clanDao = database.clanDao()

    override suspend fun savePlayer(brawlhallaId: Int, name: String) {
        playerDao.insertPlayer(Player(brawlhallaId, name))
    }

    override suspend fun deletePlayer(brawlhallaId: Int) {
        playerDao.deletePlayer(brawlhallaId)
    }

    override fun getPlayer(brawlhallaId: Int): Flow<Player?> {
        return playerDao.getPlayer(brawlhallaId)
    }

    override fun getAllPlayers(): Flow<List<Player>> {
        return playerDao.getAllPlayers()
    }

    override suspend fun saveClan(clanId: Int, name: String) {
        clanDao.insertClan(Clan(clanId, name))
    }

    override suspend fun deleteClan(clanId: Int) {
        clanDao.deleteClan(clanId)
    }

    override fun getClan(clanId: Int): Flow<Clan?> {
        return clanDao.getClan(clanId)
    }

    override fun getAllClans(): Flow<List<Clan>> {
        return clanDao.getAllClans()
    }
}