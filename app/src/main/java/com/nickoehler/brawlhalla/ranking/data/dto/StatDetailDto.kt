package com.nickoehler.brawlhalla.ranking.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatDetailDto(

    @SerialName("brawlhalla_id")
    val brawlhallaId: Long,
    @SerialName("name")
    val name: String,
    @SerialName("xp")
    val xp: Int,
    @SerialName("level")
    val level: Int,
    @SerialName("xp_percentage")
    val xpPercentage: Double,
    @SerialName("games")
    val games: Int,
    @SerialName("wins")
    val wins: Int,
    @SerialName("damagebomb")
    val damageBomb: Int,
    @SerialName("damagemine")
    val damageMine: Int,
    @SerialName("damagespikeball")
    val damageSpikeball: Int,
    @SerialName("damagesidekick")
    val damageSidekick: Int,
    @SerialName("hitsnowball")
    val hitSnowball: Int,
    @SerialName("kobomb")
    val koBomb: Int,
    @SerialName("komine")
    val koMine: Int,
    @SerialName("kospikeball")
    val koSpikeball: Int,
    @SerialName("kosidekick")
    val koSidekick: Int,
    @SerialName("kosnowball")
    val koSnowball: Int,
    @SerialName("legends")
    val legends: List<StatLegendDto>,
    @SerialName("clan")
    val clan: StatClanDto? = null
)