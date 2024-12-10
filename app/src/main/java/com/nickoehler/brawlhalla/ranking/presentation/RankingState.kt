package com.nickoehler.brawlhalla.ranking.presentation

import androidx.compose.runtime.Immutable
import com.nickoehler.brawlhalla.core.presentation.CustomAppBarState
import com.nickoehler.brawlhalla.ranking.domain.Bracket
import com.nickoehler.brawlhalla.ranking.domain.Region
import com.nickoehler.brawlhalla.ranking.presentation.models.ClanDetailUi
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingDetailUi
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingUi
import com.nickoehler.brawlhalla.ranking.presentation.models.StatDetailUi
import com.nickoehler.brawlhalla.ranking.presentation.models.StatType

@Immutable
data class RankingState(
    val isListLoading: Boolean = false,
    val isStatDetailLoading: Boolean = false,
    val isStatDetailFavorite: Boolean = false,
    val isRankingDetailLoading: Boolean = false,
    val isClanDetailLoading: Boolean = false,
    val isClanDetailFavorite: Boolean = false,
    val isLoadingMore: Boolean = false,
    val selectedStatDetail: StatDetailUi? = null,
    val selectedRankingDetail: RankingDetailUi? = null,
    val selectedClan: ClanDetailUi? = null,
    val players: List<RankingUi> = emptyList(),
    val selectedBracket: Bracket = Bracket.ONE_VS_ONE,
    val selectedRegion: Region = Region.ALL,
    val shouldLoadMore: Boolean = true,
    val selectedStatType: StatType = StatType.General,
    val rankingEnabled: Boolean = true,
    val appBarState: CustomAppBarState = CustomAppBarState()
)
