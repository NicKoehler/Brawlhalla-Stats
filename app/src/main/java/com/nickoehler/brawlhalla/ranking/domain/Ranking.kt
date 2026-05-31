package com.nickoehler.brawlhalla.ranking.domain


data class Ranking(
    val rank: Int,
    val players: List<Player>,
    val rating: Int,
    val tier: Tier,
    val wins: Int,
    val losses: Int,
    val region: Region,
    val bestRating: Int,
)