package com.nickoehler.brawlhalla.ranking.presentation.models

import com.nickoehler.brawlhalla.core.presentation.models.DisplayableDouble
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableInt
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableNumber
import com.nickoehler.brawlhalla.core.presentation.util.getMiniImageUrlFromLegendNameKey
import com.nickoehler.brawlhalla.ranking.domain.RankingLegend
import java.util.Locale

data class RankingLegendUi(
    val legendId: Int,
    val legendNameKey: String,
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
        legendNameKey.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        },
        rating.toDisplayableNumber(),
        peakRating.toDisplayableNumber(),
        tier.toTierUi(),
        wins.toDisplayableNumber(),
        games.toDisplayableNumber(),
        if (games > 0) (wins.toDouble() / games * 100).toDisplayableNumber() else null,
        getMiniImageUrlFromLegendNameKey(legendNameKey)
    )
}
