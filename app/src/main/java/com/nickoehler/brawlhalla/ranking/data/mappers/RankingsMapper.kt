package com.nickoehler.brawlhalla.ranking.data.mappers

import com.nickoehler.brawlhalla.ranking.data.dto.RankingDto
import com.nickoehler.brawlhalla.ranking.domain.Ranking

fun RankingDto.toRanking(): Ranking {
    return Ranking(
        rank,
        players.map { it.toPlayer() },
        rating ?: 0,
        (tier ?: "").toTier(),
        losses ?: 0,
        wins ?: 0,
        (region ?: "").toRegion(),
        bestRating ?: 0,
    )
}
