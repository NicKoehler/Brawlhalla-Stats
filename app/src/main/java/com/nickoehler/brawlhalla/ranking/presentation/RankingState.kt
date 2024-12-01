package com.nickoehler.brawlhalla.ranking.presentation

import androidx.compose.runtime.Immutable
import com.nickoehler.brawlhalla.core.presentation.CustomAppBarState
import com.nickoehler.brawlhalla.ranking.domain.Bracket
import com.nickoehler.brawlhalla.ranking.domain.Region
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingUi

@Immutable
data class RankingState(
    val isListLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val players: List<RankingUi> = emptyList(),
    val selectedBracket: Bracket = Bracket.ONE_VS_ONE,
    val selectedRegion: Region = Region.ALL,
    val showLoadMore: Boolean = true,
    val appBarState: CustomAppBarState = CustomAppBarState()
)
