package com.nickoehler.brawlhalla.ranking.presentation.models

import com.nickoehler.brawlhalla.core.presentation.models.DisplayableDouble
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableNumber
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableTime
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableDouble
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableNumber
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableTime
import com.nickoehler.brawlhalla.ranking.domain.StatDetail
import com.nickoehler.brawlhalla.ranking.presentation.util.toFixedUtf8

data class StatDetailUi(

    val brawlhallaId: Int,
    val name: String,
    val xp: DisplayableNumber,
    val level: Int,
    val nextLevel: Int?,
    val xpPercentage: DisplayableDouble,
    val games: DisplayableNumber,
    val wins: DisplayableNumber,
    val damageBomb: DisplayableNumber,
    val damageMine: DisplayableNumber,
    val damageSpikeball: DisplayableNumber,
    val damageSidekick: DisplayableNumber,
    val hitSnowball: DisplayableNumber,
    val koBomb: DisplayableNumber,
    val koMine: DisplayableNumber,
    val koSpikeball: DisplayableNumber,
    val koSidekick: DisplayableNumber,
    val koSnowball: DisplayableNumber,
    val legends: List<StatLegendUi>,
    val clan: StatClanUi?,
    val matchTime: DisplayableTime
)


fun StatDetail.toStatDetailUi(): StatDetailUi {
    return StatDetailUi(
        brawlhallaId,
        name.toFixedUtf8(),
        xp.toDisplayableNumber(),
        level,
        if (level == 100) null else level + 1,
        if (xpPercentage == 0.0 && level == 100) 1.0.toDisplayableDouble() else xpPercentage.toDisplayableDouble(),
        games.toDisplayableNumber(),
        wins.toDisplayableNumber(),
        damageBomb.toDisplayableNumber(),
        damageMine.toDisplayableNumber(),
        damageSpikeball.toDisplayableNumber(),
        damageSidekick.toDisplayableNumber(),
        hitSnowball.toDisplayableNumber(),
        koBomb.toDisplayableNumber(),
        koMine.toDisplayableNumber(),
        koSpikeball.toDisplayableNumber(),
        koSidekick.toDisplayableNumber(),
        koSnowball.toDisplayableNumber(),
        legends.map { it.toStatLegendUi() },
        clan = if (this.clan != null) {
            clan.toStatClanUi()
        } else null,
        legends.sumOf { it.matchTime }.toDisplayableTime()
    )
}