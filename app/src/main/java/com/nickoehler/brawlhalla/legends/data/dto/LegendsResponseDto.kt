package com.nickoehler.brawlhalla.legends.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LegendsResponseDto(
    val legends: List<LegendDetailDto>,
    @SerialName("total_pages")
    val totalPages: Int
)
