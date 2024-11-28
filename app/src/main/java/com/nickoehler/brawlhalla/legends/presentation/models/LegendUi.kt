package com.nickoehler.brawlhalla.legends.presentation.models

import com.nickoehler.brawlhalla.core.presentation.domain.WeaponUi
import com.nickoehler.brawlhalla.legends.domain.Legend
import com.nickoehler.brawlhalla.legends.domain.LegendStat
import com.nickoehler.brawlhalla.legends.presentation.util.getMiniImageUrlFromLegendNameKey
import com.nickoehler.brawlhalla.legends.presentation.util.getWeaponImageUrlFromWeaponName


data class LegendUi(
    val legendId: Int,
    val legendNameKey: String,
    val bioName: String,
    val bioAka: String,
    val weaponOne: WeaponUi,
    val weaponTwo: WeaponUi,
    val strength: Int,
    val dexterity: Int,
    val defense: Int,
    val speed: Int,
    val image: String,
)

fun Legend.toLegendUi(): LegendUi {
    return LegendUi(
        legendId = legendId,
        legendNameKey = legendNameKey,
        bioName = bioName,
        bioAka = bioAka,
        weaponOne = WeaponUi(weaponOne, getWeaponImageUrlFromWeaponName(weaponOne)),
        weaponTwo = WeaponUi(weaponTwo, getWeaponImageUrlFromWeaponName(weaponTwo)),
        strength = strength.toInt(),
        dexterity = dexterity.toInt(),
        defense = defense.toInt(),
        speed = speed.toInt(),
        image = getMiniImageUrlFromLegendNameKey(legendNameKey),
    )
}

fun LegendUi.getStat(stat: LegendStat): Int {
    return when (stat) {
        LegendStat.STRENGTH -> this.strength
        LegendStat.DEFENSE -> this.defense
        LegendStat.DEXTERITY -> this.dexterity
        LegendStat.SPEED -> this.speed
    }
}