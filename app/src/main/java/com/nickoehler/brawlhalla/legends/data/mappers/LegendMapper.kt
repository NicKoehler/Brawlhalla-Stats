package com.nickoehler.brawlhalla.legends.data.mappers

import com.nickoehler.brawlhalla.legends.data.dto.LegendDetailDto
import com.nickoehler.brawlhalla.legends.domain.LegendDetail
import com.nickoehler.brawlhalla.legends.domain.Legend
import com.nickoehler.brawltool.model.LegendDto

fun LegendDto.toLegend(): Legend {
    return Legend(
        legendId,
        legendNameKey,
        bioName,
        bioAka,
        weaponOne,
        weaponTwo,
        strength,
        dexterity,
        defense,
        speed
    )
}

fun LegendDetailDto.toLegendDetail(): LegendDetail {
    return LegendDetail(
        legendId,
        legendNameKey,
        bioName,
        bioAka,
        bioQuote,
        bioQuoteAboutAttrib,
        bioQuoteFrom,
        bioQuoteFromAttrib,
        bioText,
        botName,
        weaponOne,
        weaponTwo,
        strength,
        dexterity,
        defense,
        speed
    )
}