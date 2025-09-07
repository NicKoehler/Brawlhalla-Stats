package com.nickoehler.brawlhalla.ranking.domain

data class StatClan(
    val clanName: String,
    val clanId: Long,
    val clanXp: Long,
    val personalXp: Long,
)
