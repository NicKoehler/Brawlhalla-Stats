package com.nickoehler.brawlhalla.ranking.data.mappers

import com.nickoehler.brawlhalla.ranking.data.dto.RankingLegendDto
import com.nickoehler.brawlhalla.ranking.domain.RankingLegend

fun RankingLegendDto.toRankingLegend(): RankingLegend {
    return RankingLegend(
        legendId,
        legendNameKey,
        rating,
        peakRating,
        tier.toTier(),
        wins,
        games
    )
}