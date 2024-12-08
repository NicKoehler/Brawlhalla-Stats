package com.nickoehler.brawlhalla.ranking.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RankingDetailDto(

    @SerialName("name")
    val name: String,

    @SerialName("brawlhalla_id")
    val brawlhallaId: Int,

    @SerialName("rating")
    val rating: Int,

    @SerialName("peak_rating")
    val peakRating: Int,

    @SerialName("tier")
    val tier: String,

    @SerialName("wins")
    val wins: Int,

    @SerialName("games")
    val games: Int,

    @SerialName("region")
    val region: String,

    @SerialName("global_rank")
    val globalRank: Int,

    @SerialName("region_rank")
    val regionRank: Int,

    @SerialName("legends")
    val legends: List<RankingLegendDto>,

    @SerialName("2v2")
    val teams: List<RankingDetailTeamDto>
)
