package com.nickoehler.brawlhalla.ranking.presentation.models

import com.nickoehler.brawlhalla.clans.domain.ClanMember
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableZonedDateTime
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableZonedDateTime
import com.nickoehler.brawlhalla.ranking.presentation.util.toFixedUtf8

data class ClanMemberUi(
    val brawlhallaId: Int,
    val name: String,
    val rank: String,
    val joinDate: DisplayableZonedDateTime,
    val xp: Int
)

fun ClanMember.toClanMemberUi(): ClanMemberUi {
    return ClanMemberUi(
        brawlhallaId,
        name.toFixedUtf8(),
        rank,
        joinDate.toDisplayableZonedDateTime(),
        xp
    )
}