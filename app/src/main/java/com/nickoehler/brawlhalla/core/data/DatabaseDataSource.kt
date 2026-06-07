package com.nickoehler.brawlhalla.core.data

import com.nickoehler.brawlhalla.core.data.database.AppDatabase
import com.nickoehler.brawlhalla.core.data.database.entities.Guild
import com.nickoehler.brawlhalla.core.data.database.entities.Player
import com.nickoehler.brawlhalla.core.domain.LocalDataSource
import com.nickoehler.brawlhalla.widgets.WidgetUpdateManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class DatabaseDataSource(
    database: AppDatabase,
    private val widgetUpdateManager: WidgetUpdateManager
) : LocalDataSource {

    private val playerDao = database.playerDao()
    private val guildDao = database.guildDao()

    override suspend fun savePlayer(player: Player) {
        playerDao.insertPlayer(player)
        widgetUpdateManager.updatePlayers(playerDao.getAllPlayers().first())
    }

    override suspend fun deletePlayer(brawlhallaId: Long) {
        playerDao.deletePlayer(brawlhallaId)
        widgetUpdateManager.updatePlayers(playerDao.getAllPlayers().first())
    }

    override fun getPlayer(brawlhallaId: Long): Flow<Player?> {
        return playerDao.getPlayer(brawlhallaId)
    }

    override fun getAllPlayers(): Flow<List<Player>> {
        return playerDao.getAllPlayers()
    }

    override suspend fun saveGuild(guild: Guild) {
        guildDao.insertGuild(guild)
        widgetUpdateManager.updateGuilds(guildDao.getAllGuilds().first())
    }

    override suspend fun deleteGuild(guildId: Long) {
        guildDao.deleteGuild(guildId)
        widgetUpdateManager.updateGuilds(guildDao.getAllGuilds().first())
    }

    override fun getGuild(guildId: Long): Flow<Guild?> {
        return guildDao.getGuild(guildId)
    }

    override fun getAllGuilds(): Flow<List<Guild>> {
        return guildDao.getAllGuilds()
    }

    override suspend fun updatePlayers(players: List<Player>) {
        val updatedPlayers = players.withIndex().map { (i, player) -> player.copy(order = i) }
        playerDao.updatePlayers(updatedPlayers)
        widgetUpdateManager.updatePlayers(updatedPlayers)
    }

    override suspend fun updateGuilds(guilds: List<Guild>) {
        val updatedGuilds = guilds.withIndex().map { (i, guild) -> guild.copy(order = i) }
        guildDao.updateGuilds(updatedGuilds)
        widgetUpdateManager.updateGuilds(updatedGuilds)
    }
}
