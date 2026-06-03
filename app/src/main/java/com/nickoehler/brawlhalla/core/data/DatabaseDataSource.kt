package com.nickoehler.brawlhalla.core.data

import com.nickoehler.brawlhalla.core.data.database.AppDatabase
import com.nickoehler.brawlhalla.core.data.database.entities.Guild
import com.nickoehler.brawlhalla.core.data.database.entities.Player
import com.nickoehler.brawlhalla.core.domain.LocalDataSource
import kotlinx.coroutines.flow.Flow

class DatabaseDataSource(
    database: AppDatabase
) : LocalDataSource {

    private val playerDao = database.playerDao()
    private val guildDao = database.guildDao()

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

    override suspend fun saveGuild(guild: Guild) {
        guildDao.insertGuild(guild)
    }

    override suspend fun deleteGuild(guildId: Long) {
        guildDao.deleteGuild(guildId)
    }

    override fun getGuild(guildId: Long): Flow<Guild?> {
        return guildDao.getGuild(guildId)
    }

    override fun getAllGuilds(): Flow<List<Guild>> {
        return guildDao.getAllGuilds()
    }

    override suspend fun updatePlayers(players: List<Player>) {
        playerDao.updatePlayers(players.withIndex().map { (i, player) -> player.copy(order = i) })
    }

    override suspend fun updateGuilds(guilds: List<Guild>) {
        guildDao.updateGuilds(guilds.withIndex().map { (i, guild) -> guild.copy(order = i) })

    }


}