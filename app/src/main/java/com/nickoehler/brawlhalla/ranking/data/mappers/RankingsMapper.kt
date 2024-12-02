package com.nickoehler.brawlhalla.ranking.data.mappers

import com.nickoehler.brawlhalla.ranking.data.dto.RankingSoloDto
import com.nickoehler.brawlhalla.ranking.data.dto.RankingTeamDto
import com.nickoehler.brawlhalla.ranking.domain.Ranking

fun RankingSoloDto.toRanking(): Ranking {
    return Ranking.RankingSolo(
        rank,
        name,
        brawlhallaId,
        bestLegend,
        bestLegendGames,
        bestLegendWins,
        rating,
        tier.toTier(),
        games,
        wins,
        region.toRegion(),
        peakRating
    )
}

fun RankingTeamDto.toRanking(): Ranking {
    return Ranking.RankingTeam(
        rank,
        teamName,
        brawlhallaIdOne,
        brawlhallaIdTwo,
        rating,
        tier.toTier(),
        games,
        wins,
        region.toRegion(),
        peakRating
    )
}

