package com.nickoehler.brawlhalla.guilds.domain

import com.nickoehler.brawlhalla.core.domain.util.NetworkError
import com.nickoehler.brawlhalla.core.domain.util.Result

interface GuildDataSource {

    suspend fun getGuild(guildId: Long): Result<GuildDetail, NetworkError>

}