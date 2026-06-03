package com.nickoehler.brawlhalla.guilds.domain

import java.time.LocalDateTime

data class GuildMember(
    val brawlhallaId: Long,
    val name: String,
    val rank: GuildRankType,
    val joinDate: LocalDateTime?,
    val xp: Long,
    val guildPoints: Int?
)