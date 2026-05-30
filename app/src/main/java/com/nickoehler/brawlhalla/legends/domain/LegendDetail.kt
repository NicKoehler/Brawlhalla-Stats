package com.nickoehler.brawlhalla.legends.domain

data class LegendDetail(
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
    val weaponOne: String,
    val weaponTwo: String,
    val strength: Int,
    val dexterity: Int,
    val defense: Int,
    val speed: Int
)
