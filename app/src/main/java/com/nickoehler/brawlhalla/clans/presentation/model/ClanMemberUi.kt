package com.nickoehler.brawlhalla.clans.presentation.model

import com.nickoehler.brawlhalla.clans.domain.ClanMember
import com.nickoehler.brawlhalla.clans.domain.ClanRankType
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableNumber
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableZonedDateTime
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableNumber
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableZonedDateTime
import com.nickoehler.brawlhalla.ranking.presentation.util.toFixedUtf8

data class ClanMemberUi(
    val brawlhallaId: Int,
    val name: String,
    val rank: ClanRankType,
    val joinDate: DisplayableZonedDateTime,
    val xp: DisplayableNumber
)

fun ClanMember.toClanMemberUi(): ClanMemberUi {
    return ClanMemberUi(
        brawlhallaId,
        name.toFixedUtf8(),
        rank,
        joinDate.toDisplayableZonedDateTime(),
        xp.toDisplayableNumber()
    )
}