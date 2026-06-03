package com.nickoehler.brawlhalla.guilds.presentation.model

import com.nickoehler.brawlhalla.core.presentation.models.DisplayableLong
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableZonedDateTime
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableNumber
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableZonedDateTime
import com.nickoehler.brawlhalla.guilds.domain.GuildMember
import com.nickoehler.brawlhalla.guilds.domain.GuildRankType
import com.nickoehler.brawlhalla.ranking.presentation.util.toFixedUtf8

data class GuildMemberUi(
    val brawlhallaId: Long,
    val name: String,
    val rank: GuildRankType,
    val joinDate: DisplayableZonedDateTime?,
    val xp: DisplayableLong
)

fun GuildMember.toGuildMemberUi(): GuildMemberUi {
    return GuildMemberUi(
        brawlhallaId,
        name.toFixedUtf8(),
        rank,
        joinDate?.toDisplayableZonedDateTime(),
        xp.toDisplayableNumber()
    )
}