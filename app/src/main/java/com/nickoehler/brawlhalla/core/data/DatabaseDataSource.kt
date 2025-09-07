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

    override suspend fun savePlayer(brawlhallaId: Long, name: String) {
        playerDao.insertPlayer(Player(brawlhallaId, name))
    }

    override suspend fun deletePlayer(brawlhallaId: Long) {
        playerDao.deletePlayer(brawlhallaId)
    }

    override fun getPlayer(brawlhallaId: Long): Flow<Player?> {
        return playerDao.getPlayer(brawlhallaId)
    }

    override fun getAllPlayers(): Flow<List<Player>> {
        return playerDao.getAllPlayers()
    }

    override suspend fun saveClan(clanId: Long, name: String) {
        clanDao.insertClan(Clan(clanId, name))
    }

    override suspend fun deleteClan(clanId: Long) {
        clanDao.deleteClan(clanId)
    }

    override fun getClan(clanId: Long): Flow<Clan?> {
        return clanDao.getClan(clanId)
    }

    override fun getAllClans(): Flow<List<Clan>> {
        return clanDao.getAllClans()
    }
}