package com.nickoehler.brawlhalla.ranking.data.mappers

import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableNumber
import com.nickoehler.brawlhalla.ranking.data.dto.TeamDetailDto
import com.nickoehler.brawlhalla.ranking.domain.TeamDetail
import com.nickoehler.brawlhalla.ranking.presentation.models.TeamDetailUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toRegionUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toTierUi

fun TeamDetailDto.toTeamDetail() = TeamDetail(
    brawlhallaIdOne,
    brawlhallaIdTwo,
    usernameOne,
    usernameTwo,
    rating,
    peakRating,
    tier.toTier(),
    wins,
    games,
    region.toRegion(),
    globalRank,
)

fun TeamDetail.toTeamDetailUi() = TeamDetailUi(
    brawlhallaIdOne,
    brawlhallaIdTwo,
    usernameOne,
    usernameTwo,
    rating.toDisplayableNumber(),
    peakRating.toDisplayableNumber(),
    tier.toTierUi(),
    wins.toDisplayableNumber(),
    games.toDisplayableNumber(),
    (wins.toDouble() / games.toDouble() * 100.0).toDisplayableNumber(),
    region.toRegionUi(),
    globalRank.toDisplayableNumber(),
)