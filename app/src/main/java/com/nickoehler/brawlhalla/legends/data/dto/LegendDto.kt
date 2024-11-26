package com.nickoehler.brawltool.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LegendDto(
    @SerialName("legend_id")
    val legendId: Int,

    @SerialName("legend_name_key")
    val legendNameKey: String,

    @SerialName("bio_name")
    val bioName: String,

    @SerialName("bio_aka")
    val bioAka: String,

    @SerialName("weapon_one")
    val weaponOne: String,

    @SerialName("weapon_two")
    val weaponTwo: String,

    @SerialName("strength")
    val strength: String,

    @SerialName("dexterity")
    val dexterity: String,

    @SerialName("defense")
    val defense: String,

    @SerialName("speed")
    val speed: String
)
