package com.nickoehler.brawlhalla.clans.domain

import java.time.LocalDateTime

data class ClanMember(
    val brawlhallaId: Int,
    val name: String,
    val rank: ClanRankType,
    val joinDate: LocalDateTime,
    val xp: Int
)