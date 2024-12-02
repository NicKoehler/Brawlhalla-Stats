package com.nickoehler.brawlhalla.ranking.presentation.models

import com.nickoehler.brawlhalla.core.presentation.models.DisplayableFloat
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableNumber
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableFloat
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableNumber
import com.nickoehler.brawlhalla.ranking.domain.Ranking
import com.nickoehler.brawlhalla.ranking.presentation.util.toFixedUtf8

sealed interface RankingUi {
    data class RankingSoloUi(
        val rank: DisplayableNumber,
        val name: String,
        val brawlhallaId: Int,
        val bestLegend: Int? = null,
        val bestLegendGames: DisplayableNumber? = null,
        val bestLegendWins: DisplayableNumber? = null,
        val rating: DisplayableNumber,
        val tier: TierUi,
        val games: DisplayableNumber,
        val wins: DisplayableNumber,
        val winRate: DisplayableFloat,
        val losses: DisplayableNumber,
        val region: RegionUi,
        val peakRating: DisplayableNumber,
    ) : RankingUi

    data class RankingTeamUi(
        val rank: DisplayableNumber,
        val teamName: String,
        val brawlhallaIdOne: Int,
        val brawlhallaIdTwo: Int,
        val rating: DisplayableNumber,
        val tier: TierUi,
        val games: DisplayableNumber,
        val wins: DisplayableNumber,
        val winRate: DisplayableFloat,
        val losses: DisplayableNumber,
        val region: RegionUi,
        val peakRating: DisplayableNumber,
    ) : RankingUi
}

fun Ranking.toRankingUi(): RankingUi {
    return when (this) {
        is Ranking.RankingSolo -> this.toRankingSoloUi()
        is Ranking.RankingTeam -> this.toRankingTeamUi()
    }
}

fun Ranking.RankingSolo.toRankingSoloUi(): RankingUi.RankingSoloUi {
    return RankingUi.RankingSoloUi(
        rank.toDisplayableNumber(),
        name.toFixedUtf8(),
        brawlhallaId,
        bestLegend,
        bestLegendGames?.toDisplayableNumber(),
        bestLegendWins?.toDisplayableNumber(),
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

fun Ranking.RankingTeam.toRankingTeamUi(): RankingUi.RankingTeamUi {
    return RankingUi.RankingTeamUi(
        rank.toDisplayableNumber(),
        teamName.toFixedUtf8(),
        brawlhallaIdOne,
        brawlhallaIdTwo,
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