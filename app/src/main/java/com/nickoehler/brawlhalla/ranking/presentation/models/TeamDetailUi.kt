package com.nickoehler.brawlhalla.ranking.presentation.models

import androidx.compose.runtime.Immutable
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableDouble
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableInt

@Immutable
data class TeamDetailUi(
    val brawlhallaIdOne: Long,
    val brawlhallaIdTwo: Long,
    val usernameOne: String,
    val usernameTwo: String,
    val rating: DisplayableInt,
    val peakRating: DisplayableInt,
    val tier: TierUi,
    val wins: DisplayableInt,
    val games: DisplayableInt,
    val winRate: DisplayableDouble,
    val region: RegionUi,
    val globalRank: DisplayableInt
)

fun TeamDetailUi.getTeamMateId(brawlhallaId: Long): Long {
    if (brawlhallaId != brawlhallaIdOne) {
        return brawlhallaIdOne
    }
    return brawlhallaIdTwo
}