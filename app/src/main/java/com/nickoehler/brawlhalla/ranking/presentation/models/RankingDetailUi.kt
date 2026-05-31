package com.nickoehler.brawlhalla.ranking.presentation.models

import com.nickoehler.brawlhalla.core.presentation.models.DisplayableInt
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableNumber
import com.nickoehler.brawlhalla.ranking.domain.RankingDetail

data class RankingDetailUi(
    val name: String,
    val brawlhallaId: Long,
    val rating: DisplayableInt,
    val peakRating: DisplayableInt,
    val wins: DisplayableInt,
    val games: DisplayableInt,
    val region: RegionUi,
    val legends: List<RankingLegendUi>,
    val estimatedGlory: DisplayableInt,
    val estimatedEloReset: DisplayableInt,
)

fun RankingDetail.toRankingDetailUi(): RankingDetailUi {
    return RankingDetailUi(
        name,
        brawlhallaId,
        rating.toDisplayableNumber(),
        peakRating.toDisplayableNumber(),
        wins.toDisplayableNumber(),
        games.toDisplayableNumber(),
        region.toRegionUi(),
        legends.map { it.toRankingLegendUi() },
        estimatedGlory.toDisplayableNumber(),
        estimatedEloReset.toDisplayableNumber()
    )
}