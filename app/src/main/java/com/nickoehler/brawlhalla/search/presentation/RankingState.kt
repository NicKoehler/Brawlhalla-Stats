package com.nickoehler.brawlhalla.search.presentation

import androidx.compose.runtime.Immutable
import com.nickoehler.brawlhalla.search.domain.Bracket
import com.nickoehler.brawlhalla.search.domain.Region
import com.nickoehler.brawlhalla.search.presentation.models.RankingUi

@Immutable
data class RankingState(
    val isListLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val players: List<RankingUi> = emptyList(),
    val selectedBracket: Bracket = Bracket.ONE_VS_ONE,
    val selectedRegion: Region = Region.ALL,
    val showLoadMore: Boolean = true,
    val searchQuery: String = ""
)
