package com.nickoehler.brawlhalla.ranking.presentation.models

import com.nickoehler.brawlhalla.core.presentation.models.DisplayableDouble
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableInt
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableNumber
import com.nickoehler.brawlhalla.ranking.domain.Ranking
import com.nickoehler.brawlhalla.ranking.presentation.util.toFixedUtf8

sealed interface RankingUi {
    data class RankingSoloUi(
        val rank: DisplayableInt,
        val name: String,
        val brawlhallaId: Long,
        val bestLegend: Long? = null,
        val bestLegendGames: DisplayableInt? = null,
        val bestLegendWins: DisplayableInt? = null,
        val rating: DisplayableInt,
        val tier: TierUi,
        val games: DisplayableInt,
        val wins: DisplayableInt,
        val winRate: DisplayableDouble,
        val region: RegionUi,
        val peakRating: DisplayableInt,
    ) : RankingUi

    data class RankingTeamUi(
        val rank: DisplayableInt,
        val teamName: String,
        val brawlhallaIdOne: Long,
        val brawlhallaIdTwo: Long,
        val rating: DisplayableInt,
        val tier: TierUi,
        val games: DisplayableInt,
        val wins: DisplayableInt,
        val winRate: DisplayableDouble,
        val region: RegionUi,
        val peakRating: DisplayableInt,
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
        (wins.toDouble() / games * 100).toDisplayableNumber(),
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
        (wins.toDouble() / games * 100).toDisplayableNumber(),
        region.toRegionUi(),
        peakRating.toDisplayableNumber()
    )
}

fun RankingUi.RankingTeamUi.getTeamMateId(mainId: Long): Long {
    return if (mainId == brawlhallaIdOne)
        brawlhallaIdTwo else
        brawlhallaIdOne
}