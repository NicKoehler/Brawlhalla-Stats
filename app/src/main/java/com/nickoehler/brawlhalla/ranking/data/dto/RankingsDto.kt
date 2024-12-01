package com.nickoehler.brawlhalla.ranking.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//val rank: Int,
//val name: String,
//val brawlhallaId: Int,
//val bestLegend: Int,
//val bestLegendGames: Int,
//val bestLegendWins: Int,
//val rating: Int,
//val tier: String,
//val games: Int,
//val wins: Int,
//val region: String,
//val peakRating: Int,

@Serializable
data class RankingsDto(
    @SerialName("rank")
    val rank: Int,

    @SerialName("name")
    val name: String,

    @SerialName("brawlhalla_id")
    val brawlhallaId: Int,

    @SerialName("best_legend")
    val bestLegend: Int,

    @SerialName("best_legend_games")
    val bestLegendGames: Int,

    @SerialName("best_legend_wins")
    val bestLegendWins: Int,

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
    val peakRating: Int,

    )
