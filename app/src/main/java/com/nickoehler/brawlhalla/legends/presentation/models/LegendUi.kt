package com.nickoehler.brawlhalla.legends.presentation.models

import com.nickoehler.brawlhalla.core.presentation.domain.WeaponUi
import com.nickoehler.brawlhalla.legends.domain.Legend
import com.nickoehler.brawltool.util.getMiniImageUrlFromLegendNameKey
import com.nickoehler.brawltool.util.getWeaponImageUrlFromWeaponName


data class LegendUi(
    val legendId: Int,
    val legendNameKey: String,
    val bioName: String,
    val bioAka: String,
    val weaponOne: WeaponUi,
    val weaponTwo: WeaponUi,
    val strength: String,
    val dexterity: String,
    val defense: String,
    val speed: String,
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
        strength = strength,
        dexterity = dexterity,
        defense = defense,
        speed = speed,
        image = getMiniImageUrlFromLegendNameKey(legendNameKey),
    )
}