package com.nickoehler.brawlhalla.guilds.presentation.model

import com.nickoehler.brawlhalla.core.presentation.models.DisplayableLong
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableZonedDateTime
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableNumber
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableZonedDateTime
import com.nickoehler.brawlhalla.guilds.domain.ClanRankType
import com.nickoehler.brawlhalla.guilds.domain.GuildMember
import com.nickoehler.brawlhalla.ranking.presentation.util.toFixedUtf8

data class ClanMemberUi(
    val brawlhallaId: Long,
    val name: String,
    val rank: ClanRankType,
    val joinDate: DisplayableZonedDateTime,
    val xp: DisplayableLong
)

fun GuildMember.toClanMemberUi(): ClanMemberUi {
    return ClanMemberUi(
        brawlhallaId,
        name.toFixedUtf8(),
        rank,
        joinDate.toDisplayableZonedDateTime(),
        xp.toDisplayableNumber()
    )
}