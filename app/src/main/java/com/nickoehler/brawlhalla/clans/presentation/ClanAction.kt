package com.nickoehler.brawlhalla.clans.presentation

import com.nickoehler.brawlhalla.clans.presentation.model.ClanSortType

sealed interface ClanAction {
    data class SelectClan(val clanId: Int) : ClanAction
    data class SelectMember(val memberId: Int) : ClanAction
    data class ToggleClanFavorites(val clanId: Int, val name: String) : ClanAction
    data class SelectSortType(val sort: ClanSortType) : ClanAction
    data object ReverseSortType : ClanAction
}