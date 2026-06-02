package com.nickoehler.brawlhalla.guilds.domain

import java.time.LocalDateTime


data class ClanDetail(
    val id: Long,
    val name: String,
    val createDate: LocalDateTime,
    val xp: Long,
    val members: List<ClanMember>
)