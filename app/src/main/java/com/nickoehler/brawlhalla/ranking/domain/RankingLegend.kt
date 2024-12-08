package com.nickoehler.brawlhalla.ranking.domain

data class RankingLegend(
    val legendId: Int,
    val legendNameKey: String,
    val rating: Int,
    val peakRating: Int,
    val tier: Tier,
    val wins: Int,
    val games: Int,
)
