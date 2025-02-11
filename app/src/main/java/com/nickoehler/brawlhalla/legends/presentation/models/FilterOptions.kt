package com.nickoehler.brawlhalla.legends.presentation.models

import android.content.Context
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingLegendUi
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingUi
import com.nickoehler.brawlhalla.ranking.presentation.models.StatLegendUi

enum class FilterOptions {
    WEAPONS,
    STATS
}

sealed interface RankingModalType {
    data class StatLegend(val statLegend: StatLegendUi) : RankingModalType
    data class Team(val team: RankingUi.RankingTeamUi) : RankingModalType
    data class RankingLegend(val legend: RankingLegendUi) : RankingModalType
}

fun FilterOptions.toLocalizedString(context: Context): String {
    return context.getString(
        when (this) {
            FilterOptions.WEAPONS -> R.string.weapons
            FilterOptions.STATS -> R.string.stats
        }
    )
}

