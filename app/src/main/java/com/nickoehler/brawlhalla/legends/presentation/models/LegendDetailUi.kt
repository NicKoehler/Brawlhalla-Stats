package com.nickoehler.brawlhalla.legends.presentation.models

import com.nickoehler.brawlhalla.core.presentation.models.WeaponUi
import com.nickoehler.brawlhalla.core.presentation.util.getFullImageUrlFromLegendNameKey
import com.nickoehler.brawlhalla.core.presentation.util.getWeaponImageUrlFromWeaponName
import com.nickoehler.brawlhalla.legends.domain.LegendDetail
import com.nickoehler.brawlhalla.legends.domain.LegendStat

data class LegendDetailUi(
    val legendId: Int,
    val legendNameKey: String,
    val bioName: String,
    val bioAka: String,
    val bioQuote: String,
    val bioQuoteAboutAttrib: String,
    val bioQuoteFrom: String,
    val bioQuoteFromAttrib: String,
    val bioText: String,
    val botName: String,
    val weaponOne: WeaponUi,
    val weaponTwo: WeaponUi,
    val strength: Int,
    val dexterity: Int,
    val defense: Int,
    val speed: Int,
    val image: String,
)

fun LegendDetail.toLegendDetailUi(): LegendDetailUi {
    return LegendDetailUi(
        legendId = legendId,
        legendNameKey = legendNameKey,
        bioName = bioName,
        bioAka = bioAka,
        bioQuote = bioQuote,
        bioQuoteAboutAttrib = bioQuoteAboutAttrib,
        bioQuoteFrom = bioQuoteFrom,
        bioQuoteFromAttrib = bioQuoteFromAttrib,
        bioText = bioText.replace("\n", "\n\n"),
        botName = botName,
        weaponOne = WeaponUi(weaponOne, getWeaponImageUrlFromWeaponName(weaponOne)),
        weaponTwo = WeaponUi(weaponTwo, getWeaponImageUrlFromWeaponName(weaponTwo)),
        strength = strength.toInt(),
        dexterity = dexterity.toInt(),
        defense = defense.toInt(),
        speed = speed.toInt(),
        image = getFullImageUrlFromLegendNameKey(legendNameKey),
    )
}

fun LegendDetailUi.getStat(stat: LegendStat): Int {
    return when (stat) {
        LegendStat.STRENGTH -> this.strength
        LegendStat.DEFENSE -> this.defense
        LegendStat.DEXTERITY -> this.dexterity
        LegendStat.SPEED -> this.speed
    }
}