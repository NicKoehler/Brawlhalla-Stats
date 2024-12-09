package com.nickoehler.brawlhalla.ranking.domain

import java.time.LocalDateTime


data class ClanDetail(
    val id: Int,
    val name: String,
    val createDate: LocalDateTime,
    val xp: Int,
    val members: List<ClanMember>
)