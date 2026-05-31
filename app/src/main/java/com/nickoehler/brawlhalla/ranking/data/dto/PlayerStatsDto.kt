package com.nickoehler.brawlhalla.ranking.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerStatsDto(

    @SerialName("brawlhalla_id")
    val brawlhallaId: Long,

    @SerialName("name")
    val name: String,

    @SerialName("games")
    val games: Int,

    @SerialName("wins")
    val wins: Int,

    @SerialName("xp")
    val xp: Long? = null,

    @SerialName("xp_percentage")
    val xpPercentage: Double? = null,

    @SerialName("level")
    val level: Int? = null,

    @SerialName("damage_bomb")
    val damageBomb: Int? = null,

    @SerialName("damage_mine")
    val damageMine: Int? = null,

    @SerialName("damage_spikeball")
    val damageSpikeBall: Int? = null,

    @SerialName("damage_sidekick")
    val damageSidekick: Int? = null,

    @SerialName("hit_snowball")
    val hitSnowball: Int? = null,

    @SerialName("ko_bomb")
    val koBomb: Int? = null,

    @SerialName("ko_mine")
    val koMine: Int? = null,

    @SerialName("ko_sidekick")
    val koSidekick: Int? = null,

    @SerialName("ko_snowball")
    val koSnowball: Int? = null,

    @SerialName("ko_spikeball")
    val koSpikeBall: Int? = null,

    @SerialName("rating")
    val rating: Int? = null,

    @SerialName("peak_rating")
    val peakRating: Int? = null,

    @SerialName("tier")
    val tier: String? = null,

    @SerialName("region")
    val region: String? = null,

    @SerialName("global_rank")
    val globalRank: Int? = null,

    @SerialName("legends")
    val legends: List<StatLegendDto>
)