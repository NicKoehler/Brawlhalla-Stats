package com.nickoehler.brawlhalla.ranking.presentation.models

import com.nickoehler.brawlhalla.core.presentation.models.DisplayableDouble
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableNumber
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableDouble
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableNumber
import com.nickoehler.brawlhalla.core.presentation.util.getMiniImageUrlFromLegendNameKey
import com.nickoehler.brawlhalla.ranking.domain.StatLegend
import java.util.Locale

data class StatLegendUi(
    val legendId: Int,
    val legendNameKey: String,
    val damageDealt: DisplayableNumber,
    val damageTaken: DisplayableNumber,
    val kos: DisplayableNumber,
    val falls: DisplayableNumber,
    val suicides: DisplayableNumber,
    val teamKos: DisplayableNumber,
    val matchTime: Int,
    val games: DisplayableNumber,
    val wins: DisplayableNumber,
    val damageUnarmed: DisplayableNumber,
    val damageThrownItem: DisplayableNumber,
    val damageWeaponOne: DisplayableNumber,
    val damageWeaponTwo: DisplayableNumber,
    val damageGadgets: DisplayableNumber,
    val koUnarmed: DisplayableNumber,
    val koThrownItem: DisplayableNumber,
    val koWeaponOne: DisplayableNumber,
    val koWeaponTwo: DisplayableNumber,
    val koGadgets: DisplayableNumber,
    val timeHeldWeaponOne: DisplayableNumber,
    val timeHeldWeaponTwo: DisplayableNumber,
    val xp: DisplayableNumber,
    val level: Int,
    val nextLevel: Int?,
    val xpPercentage: DisplayableDouble,
    val image: String
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
        matchTime,
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
        timeHeldWeaponOne.toDisplayableNumber(),
        timeHeldWeaponTwo.toDisplayableNumber(),
        xp.toDisplayableNumber(),
        level,
        nextLevel = if (level < 100) level + 1 else null,
        xpPercentage.toDisplayableDouble(),
        getMiniImageUrlFromLegendNameKey(legendNameKey)
    )
}
