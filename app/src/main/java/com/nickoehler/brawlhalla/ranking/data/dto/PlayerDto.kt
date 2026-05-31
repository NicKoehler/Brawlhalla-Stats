package com.nickoehler.brawlhalla.ranking.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlayerDto(
    val id: Long,
    val username: String,
)
