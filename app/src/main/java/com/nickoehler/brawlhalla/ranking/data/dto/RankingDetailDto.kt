package com.nickoehler.brawlhalla.ranking.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RankingDetailDto(

    @SerialName("name")
    val name: String,

    @SerialName("brawlhalla_id")
    val brawlhallaId: Long,

    @SerialName("rating")
    val rating: Int,

    @SerialName("peak_rating")
    val peakRating: Int,

    @SerialName("wins")
    val wins: Int,

    @SerialName("games")
    val games: Int,

    @SerialName("region")
    val region: String,

    @SerialName("legends")
    val legends: List<RankingLegendDto>,
)
