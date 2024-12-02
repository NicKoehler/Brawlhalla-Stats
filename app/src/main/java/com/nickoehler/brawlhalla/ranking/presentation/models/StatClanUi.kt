package com.nickoehler.brawlhalla.ranking.presentation.models

import com.nickoehler.brawlhalla.ranking.domain.StatClan

data class StatClanUi(
    val clanName: String,
    val clanId: Int,
    val clanXp: Int,
    val personalXp: Int,
)

fun StatClan.toStatClanUi(): StatClanUi {
    return StatClanUi(
        clanName,
        clanId,
        clanXp,
        personalXp,
    )
}