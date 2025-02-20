package com.nickoehler.brawlhalla.clans.presentation

import androidx.compose.runtime.Immutable
import com.nickoehler.brawlhalla.clans.presentation.model.ClanDetailUi
import com.nickoehler.brawlhalla.clans.presentation.model.ClanSortType
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingUi

@Immutable
data class ClanState(
    val isClanDetailLoading: Boolean = false,
    val isClanDetailFavorite: Boolean = false,
    val selectedClan: ClanDetailUi? = null,
    val players: List<RankingUi> = emptyList(),
    val sortType: ClanSortType = ClanSortType.JoinDate,
    val reversedSortType: Boolean = false,
)
