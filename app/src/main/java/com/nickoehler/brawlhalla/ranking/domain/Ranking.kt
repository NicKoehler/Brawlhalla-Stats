package com.nickoehler.brawlhalla.ranking.domain

sealed interface Ranking {
    data class RankingSolo(
        val rank: Int,
        val name: String,
        val brawlhallaId: Long,
        val bestLegend: Long? = null,
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
        val brawlhallaIdOne: Long,
        val brawlhallaIdTwo: Long,
        val rating: Int,
        val tier: Tier,
        val games: Int,
        val wins: Int,
        val region: Region,
        val peakRating: Int,
    ) : Ranking
}