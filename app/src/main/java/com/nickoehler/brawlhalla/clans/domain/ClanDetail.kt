package com.nickoehler.brawlhalla.clans.domain

import java.time.LocalDateTime


data class ClanDetail(
    val id: Int,
    val name: String,
    val createDate: LocalDateTime,
    val xp: Int,
    val members: List<ClanMember>
)