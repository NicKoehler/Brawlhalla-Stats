package com.nickoehler.brawlhalla.ranking.data.mappers

import com.nickoehler.brawlhalla.ranking.data.dto.PlayerGuildDto
import com.nickoehler.brawlhalla.ranking.domain.PlayerGuild

fun PlayerGuildDto.toPlayerGuild() = PlayerGuild(
    guildId,
    guildName,
    personalXp,
    personalXpThisWeek,
    personalPoints,
    joinDate,
    rank,
)