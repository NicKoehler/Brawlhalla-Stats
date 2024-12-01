package com.nickoehler.brawlhalla.ranking.data.util

import com.nickoehler.brawlhalla.ranking.data.mappers.toUrlString
import com.nickoehler.brawlhalla.ranking.domain.Bracket
import com.nickoehler.brawlhalla.ranking.domain.Region

fun constructRankingsUrl(bracket: Bracket, region: Region, page: Int): String {
    return listOf(
        "/rankings",
        bracket.toUrlString(),
        region.toUrlString(),
        page.toString(),
    ).joinToString("/")
}