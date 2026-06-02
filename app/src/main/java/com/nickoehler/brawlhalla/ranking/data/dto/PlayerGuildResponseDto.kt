package com.nickoehler.brawlhalla.ranking.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerGuildResponseDto(
    @SerialName("brawlhalla_id")
    val brawlhallaId: Int,
    val guild: PlayerGuildDto
)
