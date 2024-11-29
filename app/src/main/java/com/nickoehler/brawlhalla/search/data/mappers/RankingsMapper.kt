package com.nickoehler.brawlhalla.search.data.mappers

import com.nickoehler.brawlhalla.search.data.dto.RankingsDto
import com.nickoehler.brawlhalla.search.domain.Ranking

fun RankingsDto.toRankings(): Ranking {
    return Ranking(
        rank,
        name,
        brawlhallaId,
        bestLegend,
        bestLegendGames,
        bestLegendWins,
        rating,
        tier.toTier(),
        games,
        wins,
        region.toRegion(),
        peakRating
    )
}

