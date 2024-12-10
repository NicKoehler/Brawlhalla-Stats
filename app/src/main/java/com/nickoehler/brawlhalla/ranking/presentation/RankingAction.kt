package com.nickoehler.brawlhalla.ranking.presentation

import com.nickoehler.brawlhalla.ranking.domain.Bracket
import com.nickoehler.brawlhalla.ranking.domain.Region
import com.nickoehler.brawlhalla.ranking.presentation.models.StatType

sealed interface RankingAction {
    data object LoadMore : RankingAction
    data class SelectRanking(val brawlhallaId: Int) : RankingAction
    data class SelectRegion(val region: Region) : RankingAction
    data class SelectBracket(val bracket: Bracket) : RankingAction
    data class SelectStatType(val stat: StatType) : RankingAction
    data class SelectClan(val clanId: Int) : RankingAction
    data class TogglePlayerFavorites(val brawlhallaId: Int, val name: String) : RankingAction
    data class ToggleClanFavorites(val clanId: Int, val name: String) : RankingAction
}