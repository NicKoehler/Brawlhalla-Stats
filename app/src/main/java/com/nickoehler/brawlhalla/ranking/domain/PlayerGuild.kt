package com.nickoehler.brawlhalla.ranking.domain

data class PlayerGuild(
    val guildId: Long,
    val guildName: String,
    val personalXp: Long,
    val personalXpThisWeek: Long,
    val personalPoints: Long,
    val joinDate: Long,
    val rank: String,
)
