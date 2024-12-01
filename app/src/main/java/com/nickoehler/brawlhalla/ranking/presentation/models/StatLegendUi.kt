package com.nickoehler.brawlhalla.ranking.presentation.models

import com.nickoehler.brawlhalla.ranking.domain.StatLegend

data class StatLegendUi(
    val legendId: Int,
    val legendNameKey: String,
    val damageDealt: Int,
    val damageTaken: Int,
    val kos: Int,
    val falls: Int,
    val suicides: Int,
    val teamKos: Int,
    val matchTime: Int,
    val games: Int,
    val wins: Int,
    val damageUnarmed: Int,
    val damageThrownItem: Int,
    val damageWeaponOne: Int,
    val damageWeaponTwo: Int,
    val damageGadgets: Int,
    val koUnarmed: Int,
    val koThrownItem: Int,
    val koWeaponOne: Int,
    val koWeaponTwo: Int,
    val koGadgets: Int,
    val timeHeldWeaponOne: Int,
    val timeHeldWeaponTwo: Int,
    val xp: Int,
    val level: Int,
    val xpPercentage: Double,
)


fun StatLegend.toStatLegendUi(): StatLegendUi {
    return StatLegendUi(
        legendId,
        legendNameKey,
        damageDealt,
        damageTaken,
        kos,
        falls,
        suicides,
        teamKos,
        matchTime,
        games,
        wins,
        damageUnarmed,
        damageThrownItem,
        damageWeaponOne,
        damageWeaponTwo,
        damageGadgets,
        koUnarmed,
        koThrownItem,
        koWeaponOne,
        koWeaponTwo,
        koGadgets,
        timeHeldWeaponOne,
        timeHeldWeaponTwo,
        xp,
        level,
        xpPercentage
    )
}
