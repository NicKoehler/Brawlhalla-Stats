package com.nickoehler.brawlhalla.ranking.presentation.models

import com.nickoehler.brawlhalla.ranking.domain.StatClan

data class StatClanUi(
    val clanName: String,
    val clanId: Long,
    val clanXp: Long,
    val personalXp: Long,
)

fun StatClan.toStatClanUi(): StatClanUi {
    return StatClanUi(
        clanName,
        clanId,
        clanXp,
        personalXp,
    )
}