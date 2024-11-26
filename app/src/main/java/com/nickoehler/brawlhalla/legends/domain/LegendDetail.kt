package com.nickoehler.brawlhalla.legends.domain

data class LegendDetail(
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
    val weaponOne: String,
    val weaponTwo: String,
    val strength: String,
    val dexterity: String,
    val defense: String,
    val speed: String
)
