package com.nickoehler.brawlhalla.ranking.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class RankingDetailTeamDto(
    @SerialName("global_rank")
    val rank: Int,

    @SerialName("teamname")
    val teamName: String,

    @SerialName("brawlhalla_id_one")
    val brawlhallaIdOne: Int,

    @SerialName("brawlhalla_id_two")
    val brawlhallaIdTwo: Int,

    @SerialName("rating")
    val rating: Int,

    @SerialName("tier")
    val tier: String,

    @SerialName("games")
    val games: Int,

    @SerialName("wins")
    val wins: Int,

    @SerialName("region")
    val region: Int,

    @SerialName("peak_rating")
    val peakRating: Int

)
