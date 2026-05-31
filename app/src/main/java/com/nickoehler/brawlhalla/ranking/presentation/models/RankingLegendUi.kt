package com.nickoehler.brawlhalla.ranking.presentation.models

import com.nickoehler.brawlhalla.core.presentation.models.DisplayableDouble
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableInt
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableNumber
import com.nickoehler.brawlhalla.core.presentation.util.getMiniImageUrlFromLegendId
import com.nickoehler.brawlhalla.ranking.domain.RankingLegend

data class RankingLegendUi(
    val legendId: Long,
    val rating: DisplayableInt,
    val peakRating: DisplayableInt,
    val tier: TierUi,
    val wins: DisplayableInt,
    val games: DisplayableInt,
    val winRate: DisplayableDouble?,
    val image: String,
)


fun RankingLegend.toRankingLegendUi(): RankingLegendUi {
    return RankingLegendUi(
        legendId,
        rating.toDisplayableNumber(),
        peakRating.toDisplayableNumber(),
        tier.toTierUi(),
        wins.toDisplayableNumber(),
        games.toDisplayableNumber(),
        if (games > 0) (wins.toDouble() / games * 100).toDisplayableNumber() else null,
        getMiniImageUrlFromLegendId(legendId)
    )
}
