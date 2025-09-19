package com.nickoehler.brawlhalla.ranking.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RankingLegendDto(
    @SerialName("legend_id")
    val legendId: Long,

    @SerialName("legend_name_key")
    val legendNameKey: String,

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
)
