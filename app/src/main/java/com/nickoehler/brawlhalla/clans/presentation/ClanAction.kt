package com.nickoehler.brawlhalla.clans.presentation

import com.nickoehler.brawlhalla.clans.presentation.model.ClanSortType

sealed interface ClanAction {
    data class SelectClan(val clanId: Long) : ClanAction
    data class SelectMember(val memberId: Long) : ClanAction
    data class ToggleClanFavorites(val clanId: Long, val name: String) : ClanAction
    data class SelectSortType(val sort: ClanSortType) : ClanAction
    data object ReverseSortType : ClanAction
}