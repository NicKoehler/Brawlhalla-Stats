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
    val xp: DisplayableNumber,
    val level: Int,
    val nextLevel: Int?,
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
    val clan: StatClanUi?,
    val deepLink: String
)


fun StatDetail.toStatDetailUi(): StatDetailUi {
    return StatDetailUi(
        brawlhallaId,
        name.toFixedUtf8(),
        xp.toDisplayableNumber(),
        level,
        if (level == 100) null else level + 1,
        if (xpPercentage == 0f && level == 100) 1f.toDisplayableFloat() else xpPercentage.toDisplayableFloat(),
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
        deepLink = "player?id=$brawlhallaId"
    )
}