package com.nickoehler.brawlhalla.guilds.presentation.model

import androidx.compose.ui.graphics.Color
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableLong
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableZonedDateTime
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableNumber
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableZonedDateTime
import com.nickoehler.brawlhalla.guilds.domain.GuildDetail
import com.nickoehler.brawlhalla.guilds.domain.GuildRankType
import com.nickoehler.brawlhalla.ranking.presentation.util.toFixedUtf8

data class GuildDetailUi(
    val id: Long,
    val name: String,
    val createDate: DisplayableZonedDateTime,
    val xp: DisplayableLong,
    val members: List<GuildMemberUi>
)

fun GuildDetail.toGuildDetailUi(): GuildDetailUi {
    return GuildDetailUi(
        id,
        name.toFixedUtf8(),
        createDate.toDisplayableZonedDateTime(),
        xp.toDisplayableNumber(),
        members.map { it.toGuildMemberUi() }
    )
}

fun GuildRankType.toColor(): Color {
    return when (this) {
        GuildRankType.Leader -> Color(0xFFFF5252)
        GuildRankType.Member -> Color(0xFF40C4FF)
        GuildRankType.Officer -> Color(0xFF69F0AE)
        GuildRankType.Recruit -> Color(0xFFFFAB40)
        GuildRankType.Unknown -> Color(0xFFC2C2C2)
    }
}