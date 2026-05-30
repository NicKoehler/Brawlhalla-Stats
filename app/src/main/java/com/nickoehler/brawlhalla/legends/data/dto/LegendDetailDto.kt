package com.nickoehler.brawlhalla.legends.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class LegendDetailDto(
    @SerialName("legend_id")
    val legendId: Long,

    @SerialName("legend_name")
    val legendName: String,

    @SerialName("bio_name")
    val bioName: String,

    @SerialName("bio_aka")
    val bioAka: String,

    @SerialName("bio_quote")
    val bioQuote: String,

    @SerialName("bio_quote_about_attrib")
    val bioQuoteAboutAttrib: String,

    @SerialName("bio_quote_from")
    val bioQuoteFrom: String,

    @SerialName("bio_quote_from_attrib")
    val bioQuoteFromAttrib: String,

    @SerialName("bio_text")
    val bioText: String,

    @SerialName("bot_name")
    val botName: String,

    @SerialName("weapon_one")
    val weaponOne: String,

    @SerialName("weapon_two")
    val weaponTwo: String,

    @SerialName("strength")
    val strength: Int,

    @SerialName("dexterity")
    val dexterity: Int,

    @SerialName("defense")
    val defense: Int,

    @SerialName("speed")
    val speed: Int
)
