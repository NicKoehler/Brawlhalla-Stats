package com.nickoehler.brawlhalla.ranking.presentation.models

import com.nickoehler.brawlhalla.core.presentation.models.DisplayableDouble
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableInt
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableTime
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableNumber
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableTime
import com.nickoehler.brawlhalla.core.presentation.util.getMiniImageUrlFromLegendNameKey
import com.nickoehler.brawlhalla.ranking.domain.StatLegend
import java.util.Locale

data class StatLegendUi(
    val legendId: Int,
    val legendNameKey: String,
    val damageDealt: DisplayableInt,
    val damageTaken: DisplayableInt,
    val kos: DisplayableInt,
    val falls: DisplayableInt,
    val suicides: DisplayableInt,
    val teamKos: DisplayableInt,
    val matchTime: DisplayableTime,
    val games: DisplayableInt,
    val wins: DisplayableInt,
    val damageUnarmed: DisplayableInt,
    val damageThrownItem: DisplayableInt,
    val damageWeaponOne: DisplayableInt,
    val damageWeaponTwo: DisplayableInt,
    val damageGadgets: DisplayableInt,
    val koUnarmed: DisplayableInt,
    val koThrownItem: DisplayableInt,
    val koWeaponOne: DisplayableInt,
    val koWeaponTwo: DisplayableInt,
    val koGadgets: DisplayableInt,
    val timeHeldWeaponOne: DisplayableTime,
    val timeHeldWeaponTwo: DisplayableTime,
    val xp: DisplayableInt,
    val level: DisplayableInt,
    val nextLevel: DisplayableInt?,
    val xpPercentage: DisplayableDouble,
    val image: String,
)


fun StatLegend.toStatLegendUi(): StatLegendUi {
    return StatLegendUi(
        legendId,
        legendNameKey.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        },
        damageDealt.toDisplayableNumber(),
        damageTaken.toDisplayableNumber(),
        kos.toDisplayableNumber(),
        falls.toDisplayableNumber(),
        suicides.toDisplayableNumber(),
        teamKos.toDisplayableNumber(),
        matchTime.toDisplayableTime(),
        games.toDisplayableNumber(),
        wins.toDisplayableNumber(),
        damageUnarmed.toDisplayableNumber(),
        damageThrownItem.toDisplayableNumber(),
        damageWeaponOne.toDisplayableNumber(),
        damageWeaponTwo.toDisplayableNumber(),
        damageGadgets.toDisplayableNumber(),
        koUnarmed.toDisplayableNumber(),
        koThrownItem.toDisplayableNumber(),
        koWeaponOne.toDisplayableNumber(),
        koWeaponTwo.toDisplayableNumber(),
        koGadgets.toDisplayableNumber(),
        timeHeldWeaponOne.toDisplayableTime(),
        timeHeldWeaponTwo.toDisplayableTime(),
        xp.toDisplayableNumber(),
        level.toDisplayableNumber(),
        nextLevel = if (level == 100) null else (level + 1).toDisplayableNumber(),
        if (level == 100) 1.0.toDisplayableNumber() else xpPercentage.toDisplayableNumber(),
        getMiniImageUrlFromLegendNameKey(legendNameKey)
    )
}
