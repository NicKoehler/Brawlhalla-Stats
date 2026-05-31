package com.nickoehler.brawlhalla.ranking.domain

data class RankingDetail(
    val name: String,
    val brawlhallaId: Long,
    val rating: Int,
    val peakRating: Int,
    val wins: Int,
    val games: Int,
    val region: Region,
    val legends: List<RankingLegend>,
    val estimatedGlory: Int,
    val estimatedEloReset: Int,
)
