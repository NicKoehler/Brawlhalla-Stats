package com.nickoehler.brawlhalla.ranking.presentation

import com.nickoehler.brawlhalla.ranking.domain.Bracket
import com.nickoehler.brawlhalla.ranking.domain.Region

sealed interface RankingAction {
    data object LoadMore : RankingAction
    data class SelectRanking(val brawlhallaId: Int) : RankingAction
    data class SelectRegion(val region: Region) : RankingAction
    data class SelectBracket(val bracket: Bracket) : RankingAction
}