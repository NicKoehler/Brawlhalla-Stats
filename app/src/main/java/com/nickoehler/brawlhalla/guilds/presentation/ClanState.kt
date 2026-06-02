package com.nickoehler.brawlhalla.guilds.presentation

import androidx.compose.runtime.Immutable
import com.nickoehler.brawlhalla.core.domain.util.NetworkError
import com.nickoehler.brawlhalla.guilds.presentation.model.ClanDetailUi
import com.nickoehler.brawlhalla.guilds.presentation.model.ClanSortType
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingUi

@Immutable
data class ClanState(
    val isClanDetailLoading: Boolean = false,
    val isClanDetailFavorite: Boolean = false,
    val selectedClan: ClanDetailUi? = null,
    val players: List<RankingUi> = emptyList(),
    val sortType: ClanSortType = ClanSortType.JoinDate,
    val reversedSortType: Boolean = false,
    val error: NetworkError? = null
)
