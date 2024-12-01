package com.nickoehler.brawlhalla.ranking.domain

data class Ranking(
    val rank: Int,
    val name: String,
    val brawlhallaId: Int,
    val bestLegend: Int,
    val bestLegendGames: Int,
    val bestLegendWins: Int,
    val rating: Int,
    val tier: Tier,
    val games: Int,
    val wins: Int,
    val region: Region,
    val peakRating: Int,
)
