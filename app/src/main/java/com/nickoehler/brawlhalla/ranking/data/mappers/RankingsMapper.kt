package com.nickoehler.brawlhalla.ranking.data.mappers

import com.nickoehler.brawlhalla.ranking.data.dto.RankingDto
import com.nickoehler.brawlhalla.ranking.domain.Ranking

fun RankingDto.toRanking(): Ranking {
    return Ranking(
        rank,
        players.map { it.toPlayer() },
        rating,
        tier.toTier(),
        losses,
        wins,
        region.toRegion(),
        bestRating,
    )
}
