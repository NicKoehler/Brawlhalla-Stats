package com.nickoehler.brawlhalla.ranking.presentation.models

import com.nickoehler.brawlhalla.core.presentation.models.DisplayableInt
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableNumber
import com.nickoehler.brawlhalla.ranking.domain.RankingDetail

data class RankingDetailUi(
    val name: String,
    val brawlhallaId: Long,
    val rating: DisplayableInt,
    val peakRating: DisplayableInt,
    val tier: TierUi,
    val wins: DisplayableInt,
    val games: DisplayableInt,
    val region: RegionUi,
    val globalRank: String,
    val regionRank: String,
    val legends: List<RankingLegendUi>,
    val teams: List<RankingUi.RankingTeamUi>,
    val estimatedGlory: DisplayableInt,
    val estimatedEloReset: DisplayableInt,
)

fun RankingDetail.toRankingDetailUi(): RankingDetailUi {
    return RankingDetailUi(
        name,
        brawlhallaId,
        rating.toDisplayableNumber(),
        peakRating.toDisplayableNumber(),
        tier.toTierUi(),
        wins.toDisplayableNumber(),
        games.toDisplayableNumber(),
        region.toRegionUi(),
        globalRank.toString(),
        regionRank.toString(),
        legends.map { it.toRankingLegendUi() },
        teams.map { it.toRankingTeamUi() },
        estimatedGlory.toDisplayableNumber(),
        estimatedEloReset.toDisplayableNumber()
    )
}