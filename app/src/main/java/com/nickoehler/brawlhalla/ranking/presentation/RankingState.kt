package com.nickoehler.brawlhalla.ranking.presentation

import androidx.compose.runtime.Immutable
import com.nickoehler.brawlhalla.core.presentation.CustomAppBarState
import com.nickoehler.brawlhalla.ranking.domain.Bracket
import com.nickoehler.brawlhalla.ranking.domain.Region
import com.nickoehler.brawlhalla.ranking.presentation.models.BracketUi
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingUi
import com.nickoehler.brawlhalla.ranking.presentation.models.RegionUi
import com.nickoehler.brawlhalla.ranking.presentation.models.StatDetailUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toBracketUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toRegionUi

@Immutable
data class RankingState(
    val isListLoading: Boolean = false,
    val isDetailLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val selectedRanking: StatDetailUi? = null,
    val players: List<RankingUi> = emptyList(),
    val selectedBracket: BracketUi = Bracket.ONE_VS_ONE.toBracketUi(),
    val selectedRegion: RegionUi = Region.ALL.toRegionUi(),
    val shouldLoadMore: Boolean = true,
    val appBarState: CustomAppBarState = CustomAppBarState()
)
