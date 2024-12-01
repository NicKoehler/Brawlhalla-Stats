package com.nickoehler.brawlhalla.ranking.presentation.models

import com.nickoehler.brawlhalla.core.presentation.models.DisplayableFloat
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableNumber
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableFloat
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableNumber
import com.nickoehler.brawlhalla.ranking.domain.Ranking
import com.nickoehler.brawlhalla.ranking.presentation.util.toFixedUtf8

data class RankingUi(
    val rank: DisplayableNumber,
    val name: String,
    val brawlhallaId: Int,
    val bestLegend: Int,
    val bestLegendGames: DisplayableNumber,
    val bestLegendWins: DisplayableNumber,
    val rating: DisplayableNumber,
    val tier: TierUi,
    val games: DisplayableNumber,
    val wins: DisplayableNumber,
    val winRate: DisplayableFloat,
    val losses: DisplayableNumber,
    val region: RegionUi,
    val peakRating: DisplayableNumber,
)

fun Ranking.toRankingUi(): RankingUi {
    return RankingUi(
        rank.toDisplayableNumber(),
        name.toFixedUtf8(),
        brawlhallaId,
        bestLegend,
        bestLegendGames.toDisplayableNumber(),
        bestLegendWins.toDisplayableNumber(),
        rating.toDisplayableNumber(),
        tier.toTierUi(),
        games.toDisplayableNumber(),
        wins.toDisplayableNumber(),
        (wins.toFloat() / games * 100).toDisplayableFloat(),
        (games - wins).toDisplayableNumber(),
        region.toRegionUi(),
        peakRating.toDisplayableNumber()
    )
}
