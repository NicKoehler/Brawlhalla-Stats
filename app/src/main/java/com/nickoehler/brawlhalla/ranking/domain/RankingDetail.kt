package com.nickoehler.brawlhalla.ranking.domain

data class RankingDetail(
    val name: String,
    val brawlhallaId: Int,
    val rating: Int,
    val peakRating: Int,
    val tier: Tier,
    val wins: Int,
    val games: Int,
    val region: Region,
    val globalRank: Int,
    val regionRank: Int,
    val legends: List<RankingLegend>,
    val teams: List<Ranking.RankingTeam>,
    val estimatedGlory: Int,
    val estimatedEloReset: Int,
)
