package com.nickoehler.brawlhalla.guilds.presentation

import com.nickoehler.brawlhalla.guilds.presentation.model.GuildSortType

sealed interface GuildAction {
    data class SelectGuild(val guildId: Long) : GuildAction
    data class SelectMember(val memberId: Long) : GuildAction
    data class ToggleGuildFavorites(val guildId: Long, val name: String) : GuildAction
    data class SelectSortType(val sort: GuildSortType) : GuildAction
    data object ReverseSortType : GuildAction
    data object Reload : GuildAction
}