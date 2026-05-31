package com.nickoehler.brawlhalla.ranking.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatLegendDto(

    @SerialName("legend_id")
    val legendId: Long,

    @SerialName("damage_dealt")
    val damageDealt: Int? = null,

    @SerialName("damage_taken")
    val damageTaken: Int? = null,

    @SerialName("kos")
    val kos: Int? = null,

    @SerialName("falls")
    val falls: Int? = null,

    @SerialName("suicides")
    val suicides: Int? = null,

    @SerialName("team_kos")
    val teamKos: Int? = null,

    @SerialName("match_time")
    val matchTime: Long? = null,

    @SerialName("games")
    val games: Int? = null,

    @SerialName("wins")
    val wins: Int? = null,

    @SerialName("damage_unarmed")
    val damageUnarmed: Int? = null,

    @SerialName("damage_thrown_item")
    val damageThrownItem: Int? = null,

    @SerialName("damage_weapon_one")
    val damageWeaponOne: Int? = null,

    @SerialName("damage_weapon_two")
    val damageWeaponTwo: Int? = null,

    @SerialName("damage_gadgets")
    val damageGadgets: Int? = null,

    @SerialName("ko_unarmed")
    val koUnarmed: Int? = null,

    @SerialName("ko_thrown_item")
    val koThrownItem: Int? = null,

    @SerialName("ko_weapon_one")
    val koWeaponOne: Int? = null,

    @SerialName("ko_weapon_two")
    val koWeaponTwo: Int? = null,

    @SerialName("ko_gadgets")
    val koGadgets: Int? = null,

    @SerialName("time_held_weapon_one")
    val timeHeldWeaponOne: Long? = null,

    @SerialName("time_held_weapon_two")
    val timeHeldWeaponTwo: Long? = null,

    @SerialName("xp")
    val xp: Int? = null,

    @SerialName("level")
    val level: Int? = null,

    @SerialName("xp_percentage")
    val xpPercentage: Double? = null
)
