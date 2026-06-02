package com.nickoehler.brawlhalla.ranking.data.mappers

import com.nickoehler.brawlhalla.ranking.data.dto.PlayerStatsDto
import com.nickoehler.brawlhalla.ranking.domain.PlayerGuild
import com.nickoehler.brawlhalla.ranking.domain.StatDetail

fun PlayerStatsDto.toStatDetail(guild: PlayerGuild?): StatDetail {
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
        damageSpikeBall,
        damageSidekick,
        hitSnowball,
        koBomb,
        koMine,
        koSpikeBall,
        koSidekick,
        koSnowball,
        legends.map { it.toStatLegend() },
        guild,
    )
}