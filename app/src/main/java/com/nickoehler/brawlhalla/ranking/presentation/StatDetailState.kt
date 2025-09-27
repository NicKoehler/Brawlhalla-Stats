package com.nickoehler.brawlhalla.ranking.presentation

import androidx.compose.runtime.Immutable
import com.nickoehler.brawlhalla.ranking.presentation.models.GeneralRankingSortType
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingDetailUi
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingFilterType
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingModalType
import com.nickoehler.brawlhalla.ranking.presentation.models.StatDetailUi
import com.nickoehler.brawlhalla.ranking.presentation.models.StatFilterType
import com.nickoehler.brawlhalla.ranking.presentation.models.StatLegendSortType
import com.nickoehler.brawlhalla.ranking.presentation.models.StatType

@Immutable
data class StatDetailState(
    val isStatDetailLoading: Boolean = false,
    val isStatDetailFavorite: Boolean = false,
    val isRankingDetailLoading: Boolean = false,
    val isClanDetailLoading: Boolean = false,
    val isClanDetailFavorite: Boolean = false,
    val selectedStatDetail: StatDetailUi? = null,
    val selectedRankingDetail: RankingDetailUi? = null,
    val selectedStatType: StatType = StatType.Stats,
    val selectedStatFilterType: StatFilterType = StatFilterType.General,
    val selectedRankingFilterType: RankingFilterType = RankingFilterType.Stat,
    val rankingEnabled: Boolean = true,
    val modalType: RankingModalType? = null,

    val statLegendSortType: StatLegendSortType = StatLegendSortType.Time,
    val rankedLegendSortType: GeneralRankingSortType = GeneralRankingSortType.Rating,
    val teamSortType: GeneralRankingSortType = GeneralRankingSortType.Rating,

    val statLegendSortReversed: Boolean = true,
    val rankedLegendSortReversed: Boolean = true,
    val teamSortReversed: Boolean = true,
)
