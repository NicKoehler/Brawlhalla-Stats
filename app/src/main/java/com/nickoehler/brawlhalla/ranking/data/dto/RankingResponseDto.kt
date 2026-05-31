package com.nickoehler.brawlhalla.ranking.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RankingResponseDto(
    val rankings: List<RankingDto>,
    @SerialName("total_pages")
    val totalPages: Int
)
