package com.nickoehler.brawlhalla.ranking.domain

data class RankingLegend(
    val legendId: Long,
    val rating: Int,
    val peakRating: Int,
    val tier: Tier,
    val wins: Int,
    val games: Int,
)
