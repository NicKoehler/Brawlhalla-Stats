package com.nickoehler.brawlhalla.ranking.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RankingDto(
    @SerialName("rank")
    val rank: Int,

    val players: List<PlayerDto>,

    @SerialName("rating")
    val rating: Int? = null,

    @SerialName("tier")
    val tier: String? = null,

    @SerialName("losses")
    val losses: Int? = null,

    @SerialName("wins")
    val wins: Int? = null,

    @SerialName("region")
    val region: String? = null,

    @SerialName("best_rating")
    val bestRating: Int? = null
)
