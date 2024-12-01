package com.nickoehler.brawlhalla.ranking.data.mappers

import com.nickoehler.brawlhalla.ranking.data.dto.RankingsDto
import com.nickoehler.brawlhalla.ranking.domain.Ranking

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

