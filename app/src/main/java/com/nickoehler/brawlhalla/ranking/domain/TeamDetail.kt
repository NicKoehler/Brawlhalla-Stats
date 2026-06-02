package com.nickoehler.brawlhalla.ranking.domain

data class TeamDetail(
    val brawlhallaIdOne: Long,
    val brawlhallaIdTwo: Long,
    val usernameOne: String,
    val usernameTwo: String,
    val rating: Int,
    val peakRating: Int,
    val tier: Tier,
    val wins: Int,
    val games: Int,
    val region: Region,
    val globalRank: Int
)