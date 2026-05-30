package com.nickoehler.brawlhalla.legends.data.mappers

import com.nickoehler.brawlhalla.legends.data.dto.LegendDetailDto
import com.nickoehler.brawlhalla.legends.domain.LegendDetail


fun LegendDetailDto.toLegendDetail(): LegendDetail {
    return LegendDetail(
        legendId,
        legendName,
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