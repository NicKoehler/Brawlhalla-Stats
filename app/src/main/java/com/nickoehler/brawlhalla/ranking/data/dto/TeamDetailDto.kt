package com.nickoehler.brawlhalla.ranking.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeamDetailDto(
    @SerialName("brawlhalla_id_one")
    val brawlhallaIdOne: Long,

    @SerialName("brawlhalla_id_two")
    val brawlhallaIdTwo: Long,

    @SerialName("username_one")
    val usernameOne: String,

    @SerialName("username_two")
    val usernameTwo: String,

    val rating: Int,

    @SerialName("peak_rating")
    val peakRating: Int,

    val tier: String,
    val wins: Int,
    val games: Int,
    val region: String,

    @SerialName("global_rank")
    val globalRank: Int
)
