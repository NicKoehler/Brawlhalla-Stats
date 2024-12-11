package com.nickoehler.brawlhalla.clans.presentation

sealed interface ClanAction {
    data class SelectClan(val clanId: Int) : ClanAction
    data class SelectMember(val memberId: Int) : ClanAction
    data class ToggleClanFavorites(val clanId: Int, val name: String) : ClanAction
}