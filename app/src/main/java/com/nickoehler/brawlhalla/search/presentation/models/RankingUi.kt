package com.nickoehler.brawlhalla.search.presentation.models

import com.nickoehler.brawlhalla.core.presentation.models.DisplayableNumber
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableNumber
import com.nickoehler.brawlhalla.search.domain.Ranking

data class RankingUi(
    val rank: DisplayableNumber,
    val name: String,
    val brawlhallaId: Int,
    val bestLegend: Int,
    val bestLegendGames: Int,
    val bestLegendWins: Int,
    val rating: DisplayableNumber,
    val tier: TierUi,
    val games: DisplayableNumber,
    val wins: DisplayableNumber,
    val region: RegionUi,
    val peakRating: DisplayableNumber,

    )

fun Ranking.toRankingUi(): RankingUi {
    return RankingUi(
        rank.toDisplayableNumber(),
        name,
        brawlhallaId,
        bestLegend,
        bestLegendGames,
        bestLegendWins,
        rating.toDisplayableNumber(),
        tier.toTierUi(),
        games.toDisplayableNumber(),
        wins.toDisplayableNumber(),
        region.toRegionUi(),
        peakRating.toDisplayableNumber()
    )
}
