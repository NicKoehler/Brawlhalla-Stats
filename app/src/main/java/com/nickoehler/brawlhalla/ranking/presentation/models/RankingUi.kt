package com.nickoehler.brawlhalla.ranking.presentation.models

import com.nickoehler.brawlhalla.core.presentation.models.DisplayableDouble
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableInt
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableNumber
import com.nickoehler.brawlhalla.ranking.domain.Player
import com.nickoehler.brawlhalla.ranking.domain.Ranking

data class RankingUi(
    val rank: DisplayableInt,
    val players: List<Player>,
    val rating: DisplayableInt,
    val tier: TierUi,
    val wins: DisplayableInt,
    val losses: DisplayableInt,
    val winRate: DisplayableDouble,
    val region: RegionUi,
    val bestRating: DisplayableInt,
)

fun Ranking.toRankingUi() =
    RankingUi(
        rank.toDisplayableNumber(),
        players,
        rating.toDisplayableNumber(),
        tier.toTierUi(),
        wins.toDisplayableNumber(),
        losses.toDisplayableNumber(),
        (losses.toDouble() / (wins + losses).toDouble() * 100.0).toDisplayableNumber(),
        region.toRegionUi(),
        bestRating.toDisplayableNumber(),
    )
