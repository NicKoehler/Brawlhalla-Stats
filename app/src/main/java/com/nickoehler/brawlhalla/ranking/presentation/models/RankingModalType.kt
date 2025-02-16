package com.nickoehler.brawlhalla.ranking.presentation.models

sealed interface RankingModalType {
    data class StatLegend(val statLegend: StatLegendUi) : RankingModalType
    data class Team(val team: RankingUi.RankingTeamUi) : RankingModalType
    data class RankingLegend(val legend: RankingLegendUi) : RankingModalType
}