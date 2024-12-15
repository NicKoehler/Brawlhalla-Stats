package com.nickoehler.brawlhalla.ranking.presentation

import com.nickoehler.brawlhalla.ranking.presentation.models.StatType

sealed interface StatDetailAction {
    data class SelectPlayer(val brawlhallaId: Int) : StatDetailAction
    data class SelectStatType(val stat: StatType) : StatDetailAction
    data class SelectClan(val clanId: Int) : StatDetailAction
    data class TogglePlayerFavorites(val brawlhallaId: Int, val name: String) : StatDetailAction
}