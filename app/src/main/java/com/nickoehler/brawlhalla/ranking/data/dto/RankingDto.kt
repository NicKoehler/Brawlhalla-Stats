package com.nickoehler.brawlhalla.ranking.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RankingDto(
    @SerialName("rank")
    val rank: Int,

    val players: List<PlayerDto>,
    
    @SerialName("rating")
    val rating: Int,

    @SerialName("tier")
    val tier: String,

    @SerialName("losses")
    val losses: Int,

    @SerialName("wins")
    val wins: Int,

    @SerialName("region")
    val region: String,

    @SerialName("best_rating")
    val bestRating: Int
)
