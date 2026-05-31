package com.nickoehler.brawlhalla.ranking.data.mappers

import com.nickoehler.brawlhalla.ranking.data.dto.RankingDetailDto
import com.nickoehler.brawlhalla.ranking.domain.EstimatedEloResetUseCase
import com.nickoehler.brawlhalla.ranking.domain.EstimatedGloryUseCase
import com.nickoehler.brawlhalla.ranking.domain.RankingDetail

fun RankingDetailDto.toRankingDetail(): RankingDetail {
    val getEstimatedGlory = EstimatedGloryUseCase()
    val getEstimatedEloReset = EstimatedEloResetUseCase()
    return RankingDetail(
        name,
        brawlhallaId,
        rating,
        peakRating,
        wins,
        games,
        region.toRegion(),
        legends.map { it.toRankingLegend() },
        estimatedGlory = getEstimatedGlory(
            games = legends.sumOf { it.games },
            wins = legends.sumOf { it.wins },
            peakRating = peakRating
        ),
        estimatedEloReset = getEstimatedEloReset(
            currentRating = rating
        )
    )
}
