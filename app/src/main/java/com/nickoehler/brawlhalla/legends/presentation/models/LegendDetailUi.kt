package com.nickoehler.brawlhalla.legends.presentation.models

import com.nickoehler.brawlhalla.core.presentation.models.WeaponUi
import com.nickoehler.brawlhalla.core.presentation.util.getFullImageUrlFromLegendNameKey
import com.nickoehler.brawlhalla.core.presentation.util.getMiniImageUrlFromLegendNameKey
import com.nickoehler.brawlhalla.core.presentation.util.getWeaponImageUrlFromWeaponName
import com.nickoehler.brawlhalla.legends.domain.LegendDetail
import com.nickoehler.brawlhalla.legends.domain.LegendStat

data class LegendDetailUi(
    val legendId: Long,
    val legendName: String,
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
    val imageMini: String,
    val imageFull: String,
)

fun LegendDetail.toLegendDetailUi(): LegendDetailUi {
    return LegendDetailUi(
        legendId = legendId,
        legendName = legendName,
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
        strength = strength,
        dexterity = dexterity,
        defense = defense,
        speed = speed,
        imageMini = getMiniImageUrlFromLegendNameKey(legendName),
        imageFull = getFullImageUrlFromLegendNameKey(legendName),
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