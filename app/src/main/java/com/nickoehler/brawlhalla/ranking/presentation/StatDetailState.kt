package com.nickoehler.brawlhalla.ranking.presentation

import androidx.compose.runtime.Immutable
import com.nickoehler.brawlhalla.legends.presentation.models.RankingModalType
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingDetailUi
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingFilterType
import com.nickoehler.brawlhalla.ranking.presentation.models.StatDetailUi
import com.nickoehler.brawlhalla.ranking.presentation.models.StatFilterType
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
    val selectedStatType: StatType = StatType.General,
    val selectedStatFilterType: StatFilterType = StatFilterType.Stat,
    val selectedRankingFilterType: RankingFilterType = RankingFilterType.Stat,
    val rankingEnabled: Boolean = true,
    val modalType: RankingModalType? = null,
)
