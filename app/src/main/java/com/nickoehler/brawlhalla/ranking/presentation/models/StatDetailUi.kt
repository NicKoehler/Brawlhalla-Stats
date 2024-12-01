package com.nickoehler.brawlhalla.ranking.presentation.models

import com.nickoehler.brawlhalla.core.presentation.models.DisplayableFloat
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableNumber
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableFloat
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableNumber
import com.nickoehler.brawlhalla.ranking.domain.StatDetail
import com.nickoehler.brawlhalla.ranking.presentation.util.toFixedUtf8

data class StatDetailUi(

    val brawlhallaId: Int,
    val name: String,
    val xp: Double,
    val level: Int,
    val xpPercentage: DisplayableFloat,
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
    val clan: StatClanUi?
)


fun StatDetail.toStatDetailUi(): StatDetailUi {
    return StatDetailUi(
        brawlhallaId,
        name.toFixedUtf8(),
        xp,
        level,
        xpPercentage.toDisplayableFloat(),
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
        } else null

    )
}