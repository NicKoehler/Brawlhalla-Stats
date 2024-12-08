package com.nickoehler.brawlhalla.ranking.data.mappers

import com.nickoehler.brawlhalla.ranking.data.dto.RankingDetailDto
import com.nickoehler.brawlhalla.ranking.domain.RankingDetail

fun RankingDetailDto.toRankingDetail(): RankingDetail {
    return RankingDetail(
        name,
        brawlhallaId,
        rating,
        peakRating,
        tier.toTier(),
        wins,
        games,
        region.toRegion(),
        globalRank,
        regionRank,
        legends.map { it.toRankingLegend() },
        teams.map { it.toRankingTeam() }
    )
}
