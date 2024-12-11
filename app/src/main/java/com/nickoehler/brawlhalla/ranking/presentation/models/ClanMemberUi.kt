package com.nickoehler.brawlhalla.ranking.presentation.models

import com.nickoehler.brawlhalla.clans.domain.ClanMember
import com.nickoehler.brawlhalla.ranking.presentation.util.toFixedUtf8
import java.time.ZonedDateTime
import java.util.TimeZone

data class ClanMemberUi(
    val brawlhallaId: Int,
    val name: String,
    val rank: String,
    val joinDate: ZonedDateTime,
    val xp: Int
)

fun ClanMember.toClanMemberUi(): ClanMemberUi {
    return ClanMemberUi(
        brawlhallaId,
        name.toFixedUtf8(),
        rank,
        ZonedDateTime.of(joinDate, TimeZone.getDefault().toZoneId()),
        xp
    )
}