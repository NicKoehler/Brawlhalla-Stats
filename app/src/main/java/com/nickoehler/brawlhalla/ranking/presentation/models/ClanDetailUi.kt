package com.nickoehler.brawlhalla.ranking.presentation.models

import com.nickoehler.brawlhalla.clans.domain.ClanDetail
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableNumber
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableZonedDateTime
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableNumber
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableZonedDateTime
import com.nickoehler.brawlhalla.ranking.presentation.util.toFixedUtf8

data class ClanDetailUi(
    val id: Int,
    val name: String,
    val createDate: DisplayableZonedDateTime,
    val xp: DisplayableNumber,
    val members: List<ClanMemberUi>
)

fun ClanDetail.toClanDetailUi(): ClanDetailUi {
    return ClanDetailUi(
        id,
        name.toFixedUtf8(),
        createDate.toDisplayableZonedDateTime(),
        xp.toDisplayableNumber(),
        members.map { it.toClanMemberUi() }
    )
}