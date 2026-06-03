package com.nickoehler.brawlhalla.guilds.domain

import com.nickoehler.brawlhalla.core.domain.util.NetworkError
import com.nickoehler.brawlhalla.core.domain.util.Result

interface ClanDataSource {

    suspend fun getGuild(guildId: Long): Result<GuildDetail, NetworkError>

}