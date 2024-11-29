package com.nickoehler.brawlhalla.search.data.util

import com.nickoehler.brawlhalla.search.data.mappers.toUrlString
import com.nickoehler.brawlhalla.search.domain.Bracket
import com.nickoehler.brawlhalla.search.domain.Region

fun constructRankingsUrl(bracket: Bracket, region: Region, page: Int): String {
    return listOf(
        "/rankings",
        bracket.toUrlString(),
        region.toUrlString(),
        page.toString(),
    ).joinToString("/")
}