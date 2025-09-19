package com.nickoehler.brawlhalla.ranking.data.mappers

import com.nickoehler.brawlhalla.ranking.data.dto.RankingDetailDto
import com.nickoehler.brawlhalla.ranking.domain.EstimatedEloResetUseCase
import com.nickoehler.brawlhalla.ranking.domain.EstimatedGloryUseCase
import com.nickoehler.brawlhalla.ranking.domain.RankingDetail

fun RankingDetailDto.toRankingDetail(): RankingDetail {
    val getEstimatedGlory = EstimatedGloryUseCase()
    val getEstimatedEloReset = EstimatedEloResetUseCase()
    val ratings: List<Int> =
        listOf(peakRating) + teams.map { it.peakRating } + legends.map { it.peakRating }
    return RankingDetail(
        name,
        brawlhallaId,
        rating,
        peakRating,
        tier.toTier(),
        wins,
        games,
        region.toRegion(),
        globalRank,
        regionRank,
        legends.map { it.toRankingLegend() },
        teams.map { it.toRankingTeam() }.distinct(),
        estimatedGlory = getEstimatedGlory(
            games = legends.sumOf { it.games },
            wins = legends.sumOf { it.wins },
            peakRating = ratings.maxOf { it }
        ),
        estimatedEloReset = getEstimatedEloReset(
            currentRating = rating
        )
    )
}
