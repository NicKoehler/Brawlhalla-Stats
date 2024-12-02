package com.nickoehler.brawlhalla.ranking.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RankingSoloDto(
    @SerialName("rank")
    val rank: Int,

    @SerialName("name")
    val name: String,

    @SerialName("brawlhalla_id")
    val brawlhallaId: Int,

    @SerialName("best_legend")
    val bestLegend: Int? = null,

    @SerialName("best_legend_games")
    val bestLegendGames: Int? = null,

    @SerialName("best_legend_wins")
    val bestLegendWins: Int? = null,

    @SerialName("rating")
    val rating: Int,

    @SerialName("tier")
    val tier: String,

    @SerialName("games")
    val games: Int,

    @SerialName("wins")
    val wins: Int,

    @SerialName("region")
    val region: String,

    @SerialName("peak_rating")
    val peakRating: Int
)
