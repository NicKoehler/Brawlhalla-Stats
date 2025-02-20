package com.nickoehler.brawlhalla.clans.presentation.model

import androidx.compose.ui.graphics.Color
import com.nickoehler.brawlhalla.clans.domain.ClanDetail
import com.nickoehler.brawlhalla.clans.domain.ClanRankType
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

fun ClanRankType.toColor(): Color {
    return when (this) {
        ClanRankType.Leader -> Color(0xFFFF5252)
        ClanRankType.Member -> Color(0xFF40C4FF)
        ClanRankType.Officer -> Color(0xFF69F0AE)
        ClanRankType.Recruit -> Color(0xFFFFAB40)
        ClanRankType.Unknown -> Color(0xFFC2C2C2)
    }
}