package com.nickoehler.brawlhalla.ranking.domain

sealed interface Ranking {
    data class RankingSolo(
        val rank: Int,
        val name: String,
        val brawlhallaId: Int,
        val bestLegend: Int? = null,
        val bestLegendGames: Int? = null,
        val bestLegendWins: Int? = null,
        val rating: Int,
        val tier: Tier,
        val games: Int,
        val wins: Int,
        val region: Region,
        val peakRating: Int,
    ) : Ranking

    data class RankingTeam(
        val rank: Int,
        val teamName: String,
        val brawlhallaIdOne: Int,
        val brawlhallaIdTwo: Int,
        val rating: Int,
        val tier: Tier,
        val games: Int,
        val wins: Int,
        val region: Region,
        val peakRating: Int,
    ) : Ranking
}