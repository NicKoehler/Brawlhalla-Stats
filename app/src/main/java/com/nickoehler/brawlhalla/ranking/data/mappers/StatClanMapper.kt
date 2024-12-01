package com.nickoehler.brawlhalla.ranking.data.mappers

import com.nickoehler.brawlhalla.ranking.data.dto.StatClanDto
import com.nickoehler.brawlhalla.ranking.domain.StatClan

fun StatClanDto.toStatClan(): StatClan {
    return StatClan(
        clanName,
        clanId,
        clanXp,
        personalXp,
    )
}