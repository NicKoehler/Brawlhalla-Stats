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

    override suspend fun savePlayer(player: Player) {
        playerDao.insertPlayer(player)
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

    override suspend fun saveClan(clan: Clan) {
        clanDao.insertClan(clan)
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

    override suspend fun updatePlayers(players: List<Player>) {
        playerDao.updatePlayers(players.withIndex().map { (i, player) -> player.copy(order = i) })
    }

    override suspend fun updateClans(clans: List<Clan>) {
        clanDao.updateClans(clans.withIndex().map { (i, clan) -> clan.copy(order = i) })

    }


}