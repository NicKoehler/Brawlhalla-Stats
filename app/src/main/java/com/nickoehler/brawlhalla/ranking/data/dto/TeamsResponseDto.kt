package com.nickoehler.brawlhalla.ranking.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeamsResponseDto(
    @SerialName("brawlhalla_id")
    val brawlhallaId: Long,

    val teams: Team2v2Response
)

@Serializable
data class Team2v2Response(
    @SerialName("ranked_2v2")
    val ranked2v2: List<TeamDetailDto>
)