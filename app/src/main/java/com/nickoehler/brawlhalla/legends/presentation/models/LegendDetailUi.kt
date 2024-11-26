package com.nickoehler.brawlhalla.legends.presentation.models

import com.nickoehler.brawlhalla.core.presentation.domain.WeaponUi
import com.nickoehler.brawlhalla.legends.domain.LegendDetail
import com.nickoehler.brawltool.util.getFullImageUrlFromLegendNameKey
import com.nickoehler.brawltool.util.getWeaponImageUrlFromWeaponName

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
    val strength: String,
    val dexterity: String,
    val defense: String,
    val speed: String,
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
        strength = strength,
        dexterity = dexterity,
        defense = defense,
        speed = speed,
        image = getFullImageUrlFromLegendNameKey(legendNameKey)
    )
}