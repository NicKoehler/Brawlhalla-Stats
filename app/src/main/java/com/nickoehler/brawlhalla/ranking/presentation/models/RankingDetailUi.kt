package com.nickoehler.brawlhalla.ranking.presentation.models

import com.nickoehler.brawlhalla.core.presentation.models.DisplayableNumber
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableNumber
import com.nickoehler.brawlhalla.ranking.domain.RankingDetail

data class RankingDetailUi(
    val name: String,
    val brawlhallaId: Int,
    val rating: DisplayableNumber,
    val peakRating: DisplayableNumber,
    val tier: TierUi,
    val wins: DisplayableNumber,
    val games: DisplayableNumber,
    val region: RegionUi,
    val globalRank: Int,
    val regionRank: Int,
    val legends: List<RankingLegendUi>,
    val teams: List<RankingUi.RankingTeamUi>
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
        globalRank,
        regionRank,
        legends.map { it.toRankingLegendUi() },
        teams.map { it.toRankingTeamUi() }
    )
}