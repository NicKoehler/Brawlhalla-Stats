package com.nickoehler.brawlhalla.guilds.domain

import java.time.LocalDateTime

data class ClanMember(
    val brawlhallaId: Long,
    val name: String,
    val rank: ClanRankType,
    val joinDate: LocalDateTime,
    val xp: Long
)