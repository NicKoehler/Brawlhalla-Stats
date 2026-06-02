package com.nickoehler.brawlhalla.ranking.presentation.models

import com.nickoehler.brawlhalla.core.presentation.models.DisplayableLong
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableZonedDateTime
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableNumber
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableZonedDateTime
import com.nickoehler.brawlhalla.core.presentation.models.toLocalDateTime
import com.nickoehler.brawlhalla.ranking.domain.PlayerGuild

data class PlayerGuildUi(
    val guildId: Long,
    val guildName: String,
    val personalXp: DisplayableLong,
    val personalXpThisWeek: DisplayableLong,
    val personalPoints: DisplayableLong,
    val joinDate: DisplayableZonedDateTime,
    val rank: String,
)

fun PlayerGuild.toPlayerGuildUi(): PlayerGuildUi {
    return PlayerGuildUi(
        guildId,
        guildName,
        personalXp.toDisplayableNumber(),
        personalXpThisWeek.toDisplayableNumber(),
        personalPoints.toDisplayableNumber(),
        joinDate.toLocalDateTime().toDisplayableZonedDateTime(),
        rank,
    )
}