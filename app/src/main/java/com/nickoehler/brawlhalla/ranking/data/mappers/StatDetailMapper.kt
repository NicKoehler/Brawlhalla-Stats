package com.nickoehler.brawlhalla.ranking.data.mappers

import com.nickoehler.brawlhalla.ranking.data.dto.StatDetailDto
import com.nickoehler.brawlhalla.ranking.domain.StatDetail

fun StatDetailDto.toStatDetail(): StatDetail {
    return StatDetail(
        brawlhallaId,
        name,
        xp,
        level,
        xpPercentage,
        games,
        wins,
        damageBomb,
        damageMine,
        damageSpikeball,
        damageSidekick,
        hitSnowball,
        koBomb,
        koMine,
        koSpikeball,
        koSidekick,
        koSnowball,
        legends.map { it.toStatLegend() },
        clan = if (clan != null)
            this.clan.toStatClan()
        else
            null
    )
}