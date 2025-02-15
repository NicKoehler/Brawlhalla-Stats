package com.nickoehler.brawlhalla.ranking.presentation

import com.nickoehler.brawlhalla.legends.presentation.models.RankingModalType
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingFilterType
import com.nickoehler.brawlhalla.ranking.presentation.models.StatFilterType
import com.nickoehler.brawlhalla.ranking.presentation.models.StatType

sealed interface StatDetailAction {
    data class SelectPlayer(val brawlhallaId: Int) : StatDetailAction
    data class SelectStatType(val stat: StatType) : StatDetailAction
    data class SelectClan(val clanId: Int) : StatDetailAction
    data class SelectRankingFilterType(val type: RankingFilterType) : StatDetailAction
    data class SelectStatFilterType(val type: StatFilterType) : StatDetailAction
    data class TogglePlayerFavorites(val brawlhallaId: Int, val name: String) : StatDetailAction
    data class SelectRankingModalType(val modalType: RankingModalType?) : StatDetailAction
}