package com.nickoehler.brawlhalla.ranking.presentation

import com.nickoehler.brawlhalla.ranking.presentation.models.BracketUi
import com.nickoehler.brawlhalla.ranking.presentation.models.RegionUi

sealed interface RankingAction {
    data object LoadMore : RankingAction
    data class SelectRanking(val id: Int) : RankingAction
    data class SelectRegion(val region: RegionUi) : RankingAction
    data class SelectBracket(val bracket: BracketUi) : RankingAction
}