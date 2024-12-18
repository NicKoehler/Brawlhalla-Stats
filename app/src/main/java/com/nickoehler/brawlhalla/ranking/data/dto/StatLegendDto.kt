package com.nickoehler.brawlhalla.ranking.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class StatLegendDto(
    @SerialName("legend_id")
    val legendId: Int,
    @SerialName("legend_name_key")
    val legendNameKey: String,
    @SerialName("damagedealt")
    val damageDealt: Int,
    @SerialName("damagetaken")
    val damageTaken: Int,
    @SerialName("kos")
    val kos: Int,
    @SerialName("falls")
    val falls: Int,
    @SerialName("suicides")
    val suicides: Int,
    @SerialName("teamkos")
    val teamKos: Int,
    @SerialName("matchtime")
    val matchTime: Int,
    @SerialName("games")
    val games: Int,
    @SerialName("wins")
    val wins: Int,
    @SerialName("damageunarmed")
    val damageUnarmed: Int,
    @SerialName("damagethrownitem")
    val damageThrownItem: Int,
    @SerialName("damageweaponone")
    val damageWeaponOne: Int,
    @SerialName("damageweapontwo")
    val damageWeaponTwo: Int,
    @SerialName("damagegadgets")
    val damageGadgets: Int,
    @SerialName("kounarmed")
    val koUnarmed: Int,
    @SerialName("kothrownitem")
    val koThrownItem: Int,
    @SerialName("koweaponone")
    val koWeaponOne: Int,
    @SerialName("koweapontwo")
    val koWeaponTwo: Int,
    @SerialName("kogadgets")
    val koGadgets: Int,
    @SerialName("timeheldweaponone")
    val timeHeldWeaponOne: Int,
    @SerialName("timeheldweapontwo")
    val timeHeldWeaponTwo: Int,
    @SerialName("xp")
    val xp: Int,
    @SerialName("level")
    val level: Int,
    @SerialName("xp_percentage")
    val xpPercentage: Float,
)
