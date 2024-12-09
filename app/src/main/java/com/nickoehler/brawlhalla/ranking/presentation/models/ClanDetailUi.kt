package com.nickoehler.brawlhalla.ranking.presentation.models

import com.nickoehler.brawlhalla.core.presentation.models.DisplayableNumber
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableNumber
import com.nickoehler.brawlhalla.ranking.domain.ClanDetail
import com.nickoehler.brawlhalla.ranking.presentation.util.toFixedUtf8
import java.time.ZonedDateTime
import java.util.TimeZone

data class ClanDetailUi(
    val id: Int,
    val name: String,
    val createDate: ZonedDateTime,
    val xp: DisplayableNumber,
    val members: List<ClanMemberUi>
)

fun ClanDetail.toClanDetailUi(): ClanDetailUi {
    return ClanDetailUi(
        id,
        name.toFixedUtf8(),
        ZonedDateTime.of(createDate, TimeZone.getDefault().toZoneId()),
        xp.toDisplayableNumber(),
        members.map { it.toClanMemberUi() }
    )
}