package com.nickoehler.brawlhalla.ranking.data.util

import com.nickoehler.brawlhalla.ranking.domain.GameMode
import com.nickoehler.brawlhalla.ranking.domain.Region

fun constructRankingsUrl(gameMode: GameMode, region: Region, page: Int): String {
    return listOf(
        "/v1/leaderboard/ranked",

        ).joinToString("/")
}