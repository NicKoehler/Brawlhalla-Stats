package com.nickoehler.brawlhalla.ranking.presentation.models

import com.nickoehler.brawlhalla.core.presentation.models.DisplayableDouble
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableInt
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableLong
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableTime
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableNumber
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableTime
import com.nickoehler.brawlhalla.ranking.domain.StatDetail
import com.nickoehler.brawlhalla.ranking.presentation.util.toFixedUtf8

data class StatDetailUi(

    val brawlhallaId: Long,
    val name: String,
    val xp: DisplayableLong?,
    val level: Int?,
    val nextLevel: Int?,
    val xpPercentage: DisplayableDouble?,
    val games: DisplayableInt,
    val wins: DisplayableInt,
    val damageBomb: DisplayableInt?,
    val damageMine: DisplayableInt?,
    val damageSpikeBall: DisplayableInt?,
    val damageSidekick: DisplayableInt?,
    val hitSnowball: DisplayableInt?,
    val koBomb: DisplayableInt?,
    val koMine: DisplayableInt?,
    val koSpikeBall: DisplayableInt?,
    val koSidekick: DisplayableInt?,
    val koSnowball: DisplayableInt?,
    val legends: List<StatLegendUi>,
    val matchTime: DisplayableTime,
    val guild: PlayerGuildUi?
)


fun StatDetail.toStatDetailUi(): StatDetailUi {
    return StatDetailUi(
        brawlhallaId,
        name.toFixedUtf8(),
        xp?.toDisplayableNumber(),
        level,
        if (level == 100) null else level?.plus(1),
        if (xpPercentage == 0.0 && level == 100) 1.0.toDisplayableNumber() else xpPercentage?.toDisplayableNumber(),
        games.toDisplayableNumber(),
        wins.toDisplayableNumber(),
        damageBomb?.toDisplayableNumber(),
        damageMine?.toDisplayableNumber(),
        damageSpikeBall?.toDisplayableNumber(),
        damageSidekick?.toDisplayableNumber(),
        hitSnowball?.toDisplayableNumber(),
        koBomb?.toDisplayableNumber(),
        koMine?.toDisplayableNumber(),
        koSpikeBall?.toDisplayableNumber(),
        koSidekick?.toDisplayableNumber(),
        koSnowball?.toDisplayableNumber(),
        legends.map { it.toStatLegendUi() },
        legends.sumOf { it.matchTime ?: 0L }.toDisplayableTime(),
        guild?.toPlayerGuildUi()
    )
}