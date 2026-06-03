package com.nickoehler.brawlhalla.guilds.presentation

import androidx.compose.runtime.Immutable
import com.nickoehler.brawlhalla.core.domain.util.NetworkError
import com.nickoehler.brawlhalla.guilds.presentation.model.GuildDetailUi
import com.nickoehler.brawlhalla.guilds.presentation.model.GuildSortType
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingUi

@Immutable
data class GuildState(
    val isGuildDetailLoading: Boolean = false,
    val isGuildDetailFavorite: Boolean = false,
    val selectedGuild: GuildDetailUi? = null,
    val players: List<RankingUi> = emptyList(),
    val sortType: GuildSortType = GuildSortType.JoinDate,
    val reversedSortType: Boolean = false,
    val error: NetworkError? = null
)
