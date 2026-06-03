package com.nickoehler.brawlhalla.ranking.presentation.models

import androidx.compose.runtime.Immutable
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableDouble
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableInt
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableNumber
import com.nickoehler.brawlhalla.ranking.domain.Player
import com.nickoehler.brawlhalla.ranking.domain.Ranking
import com.nickoehler.brawlhalla.ranking.presentation.util.toFixedUtf8

@Immutable
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
        players.map { it.copy(username = it.username.toFixedUtf8()) },
        rating.toDisplayableNumber(),
        tier.toTierUi(),
        wins.toDisplayableNumber(),
        losses.toDisplayableNumber(),
        (losses.toDouble() / (wins + losses).toDouble() * 100.0).toDisplayableNumber(),
        region.toRegionUi(),
        bestRating.toDisplayableNumber(),
    )
