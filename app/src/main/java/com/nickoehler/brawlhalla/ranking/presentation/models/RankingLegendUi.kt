package com.nickoehler.brawlhalla.ranking.presentation.models

import com.nickoehler.brawlhalla.core.presentation.models.DisplayableNumber
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableNumber
import com.nickoehler.brawlhalla.ranking.domain.RankingLegend

data class RankingLegendUi(
    val legendId: Int,
    val legendNameKey: String,
    val rating: DisplayableNumber,
    val peakRating: DisplayableNumber,
    val tier: TierUi,
    val wins: DisplayableNumber,
    val games: DisplayableNumber,
)


fun RankingLegend.toRankingLegendUi(): RankingLegendUi {
    return RankingLegendUi(
        legendId,
        legendNameKey,
        rating.toDisplayableNumber(),
        peakRating.toDisplayableNumber(),
        tier.toTierUi(),
        wins.toDisplayableNumber(),
        games.toDisplayableNumber()
    )
}
