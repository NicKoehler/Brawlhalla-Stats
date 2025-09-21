package com.nickoehler.brawlhalla.ranking.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickoehler.brawlhalla.core.data.database.entities.Player
import com.nickoehler.brawlhalla.core.domain.LocalDataSource
import com.nickoehler.brawlhalla.core.domain.util.onError
import com.nickoehler.brawlhalla.core.domain.util.onSuccess
import com.nickoehler.brawlhalla.core.presentation.UiEvent
import com.nickoehler.brawlhalla.ranking.domain.RankingMessage
import com.nickoehler.brawlhalla.ranking.domain.RankingsDataSource
import com.nickoehler.brawlhalla.ranking.presentation.models.GeneralRankingSortType
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingFilterType
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingLegendUi
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingModalType
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingSortType
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingUi
import com.nickoehler.brawlhalla.ranking.presentation.models.StatFilterType
import com.nickoehler.brawlhalla.ranking.presentation.models.StatLegendSortType
import com.nickoehler.brawlhalla.ranking.presentation.models.StatLegendUi
import com.nickoehler.brawlhalla.ranking.presentation.models.StatType
import com.nickoehler.brawlhalla.ranking.presentation.models.toRankingDetailUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toStatDetailUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class StatDetailViewModel(
    private val brawlhallaId: Long,
    private val rankingsDataSource: RankingsDataSource,
    private val database: LocalDataSource,
) : ViewModel() {
    private val _state = MutableStateFlow(StatDetailState())
    val state = _state.onStart {
        selectStatDetail(brawlhallaId)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000L),
        StatDetailState()
    )

    private val _uiEvents = Channel<UiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    private fun selectStatDetail(id: Long) {
        if (_state.value.selectedStatDetail?.brawlhallaId == id) {
            return
        }
        _state.update { state ->
            state.copy(
                rankingEnabled = true,
                selectedStatDetail = null,
                isStatDetailLoading = true,
                isStatDetailFavorite = false,
                selectedStatType = StatType.General
            )
        }
        viewModelScope.launch {
            rankingsDataSource.getStat(id).onSuccess { stat ->
                database.getPlayer(brawlhallaId = id).collect { player ->
                    _state.update { state ->
                        val statDetail = stat.toStatDetailUi()
                        state.copy(
                            isStatDetailFavorite = player != null,
                            isStatDetailLoading = false,
                            selectedStatDetail = statDetail.copy(
                                legends = sortStatLegends(
                                    state.statLegendSortType,
                                    state.statLegendSortReversed,
                                    statDetail.legends
                                )
                            )
                        )
                    }
                }
            }.onError { error ->
                _state.update { state ->
                    state.copy(
                        isStatDetailLoading = false,
                    )
                }
                _uiEvents.send(UiEvent.Error(error))
            }
        }
    }

    private fun selectRankingDetail(id: Long) {
        if (_state.value.selectedRankingDetail?.brawlhallaId == id) {
            return
        }

        _state.update { state ->
            state.copy(isRankingDetailLoading = true, selectedRankingDetail = null)
        }

        viewModelScope.launch {
            rankingsDataSource.getRanked(id).onSuccess { stat ->
                _state.update { state ->
                    val rankingDetail = stat.toRankingDetailUi()
                    state.copy(
                        isRankingDetailLoading = false,
                        selectedRankingDetail = rankingDetail.copy(
                            legends = sortRankingLegends(
                                state.rankedLegendSortType,
                                state.statLegendSortReversed,
                                rankingDetail.legends
                            ),
                            teams = sortTeams(
                                state.teamSortType,
                                state.teamSortReversed,
                                rankingDetail.teams
                            )
                        )
                    )
                }
            }.onError { error ->
                _state.update { state ->
                    state.copy(
                        isRankingDetailLoading = false,
                        rankingEnabled = false,
                        selectedStatType = StatType.General
                    )
                }
                _uiEvents.send(UiEvent.Error(error))
            }
        }
    }

    private fun selectStatType(stat: StatType) {
        if (stat == StatType.Ranking && _state.value.selectedStatDetail != null) {
            selectRankingDetail(_state.value.selectedStatDetail!!.brawlhallaId)
        }

        _state.update { state ->
            state.copy(selectedStatType = stat)
        }
    }

    fun onStatDetailAction(action: StatDetailAction) {
        when (action) {

            is StatDetailAction.SelectPlayer -> selectStatDetail(action.brawlhallaId)
            is StatDetailAction.SelectStatType -> selectStatType(action.stat)
            is StatDetailAction.SelectRankingModalType -> selectRankingModalType(action.modalType)
            is StatDetailAction.SelectRankingFilterType -> selectRankingFilterType(action.type)
            is StatDetailAction.SelectStatFilterType -> selectStatFilterType(action.type)
            is StatDetailAction.TogglePlayerFavorites -> togglePlayerFavorites(
                action.brawlhallaId,
                action.name
            )

            is StatDetailAction.SortBy -> sortBy(action.sortType)

            StatDetailAction.RankingLegendSortTypeReversed -> rankingLegendSortTypeReversed()
            StatDetailAction.StatLegendSortTypeReversed -> statLegendSortTypeReversed()
            StatDetailAction.TeamSortTypeReversed -> teamSortTypeReversed()
        }
    }

    private fun statLegendSortTypeReversed() {
        val currentState = state.value
        val currentStatDetail = currentState.selectedStatDetail
        val reversed = !currentState.statLegendSortReversed
        val legends = currentStatDetail?.legends.orEmpty()
        _state.update { state ->
            state.copy(
                statLegendSortReversed = reversed,
                selectedStatDetail = currentStatDetail?.copy(legends = legends.reversed())
            )
        }
    }

    private fun rankingLegendSortTypeReversed() {
        val currentState = state.value
        val currentRankingDetail = currentState.selectedRankingDetail
        val reversed = !currentState.rankedLegendSortReversed
        val legends = currentRankingDetail?.legends.orEmpty()
        _state.update { state ->
            state.copy(
                rankedLegendSortReversed = reversed,
                selectedRankingDetail = currentRankingDetail?.copy(legends = legends.reversed())
            )
        }
    }

    private fun teamSortTypeReversed() {
        val currentState = state.value
        val currentRankingDetail = currentState.selectedRankingDetail
        val reversed = !currentState.teamSortReversed
        val teams = currentRankingDetail?.teams.orEmpty()
        _state.update { state ->
            state.copy(
                teamSortReversed = reversed,
                selectedRankingDetail = currentRankingDetail?.copy(teams = teams.reversed())
            )
        }
    }

    private fun sortBy(sortType: RankingSortType) {
        val currentStatDetail = state.value.selectedStatDetail
        val currentRankingDetail = state.value.selectedRankingDetail

        _state.update { state ->
            when (sortType) {
                is RankingSortType.RankingLegend -> {
                    val currentRankingLegends = currentRankingDetail?.legends.orEmpty()
                    val reversed = state.rankedLegendSortReversed
                    state.copy(
                        rankedLegendSortType = sortType.rankingLegend,
                        selectedRankingDetail = currentRankingDetail?.copy(
                            legends = sortRankingLegends(
                                sortType.rankingLegend,
                                reversed,
                                currentRankingLegends
                            )
                        )
                    )
                }

                is RankingSortType.StatLegend -> {
                    val currentStatLegends = currentStatDetail?.legends.orEmpty()
                    val reversed = state.statLegendSortReversed
                    state.copy(
                        statLegendSortType = sortType.statLegend,
                        selectedStatDetail = currentStatDetail?.copy(
                            legends = sortStatLegends(
                                sortType.statLegend,
                                reversed,
                                currentStatLegends
                            )
                        )
                    )
                }

                is RankingSortType.Team -> {
                    val currentTeams = currentRankingDetail?.teams.orEmpty()
                    val reversed = state.teamSortReversed
                    state.copy(
                        teamSortType = sortType.team,
                        selectedRankingDetail = currentRankingDetail?.copy(
                            teams = sortTeams(
                                sortType.team,
                                reversed,
                                currentTeams
                            )
                        )
                    )
                }
            }

        }
    }

    private fun sortStatLegends(
        sortType: StatLegendSortType,
        reversed: Boolean,
        currentStatLegends: List<StatLegendUi>
    ): List<StatLegendUi> {
        val list = when (sortType) {
            StatLegendSortType.Alpha -> currentStatLegends.sortedBy { it.legendNameKey }
            StatLegendSortType.Level -> currentStatLegends.sortedBy { it.level.value }
            StatLegendSortType.Time -> currentStatLegends.sortedBy { it.matchTime.value }
            StatLegendSortType.Games -> currentStatLegends.sortedBy { it.games.value }
            StatLegendSortType.Wins -> currentStatLegends.sortedBy { it.wins.value }
        }

        return if (reversed) list.reversed() else list
    }


    private fun sortRankingLegends(
        sortType: GeneralRankingSortType,
        reversed: Boolean,
        elements: List<RankingLegendUi>
    ): List<RankingLegendUi> {
        val list = when (sortType) {
            GeneralRankingSortType.Alpha -> elements.sortedBy { it.legendNameKey }
            GeneralRankingSortType.PeakRating -> elements.sortedBy { it.peakRating.value }
            GeneralRankingSortType.Rating -> elements.sortedBy { it.rating.value }
            GeneralRankingSortType.WinRate -> elements.sortedBy { it.winRate?.value }
            GeneralRankingSortType.Games -> elements.sortedBy { it.games.value }
            GeneralRankingSortType.Wins -> elements.sortedBy { it.wins.value }
        }
        return if (reversed) list.reversed() else list
    }

    private fun sortTeams(
        sortType: GeneralRankingSortType,
        reversed: Boolean,
        elements: List<RankingUi.RankingTeamUi>
    ): List<RankingUi.RankingTeamUi> {
        val list = when (sortType) {
            GeneralRankingSortType.Alpha -> elements.sortedBy { it.teamName }
            GeneralRankingSortType.PeakRating -> elements.sortedBy { it.peakRating.value }
            GeneralRankingSortType.Rating -> elements.sortedBy { it.rating.value }
            GeneralRankingSortType.WinRate -> elements.sortedBy { it.winRate.value }
            GeneralRankingSortType.Games -> elements.sortedBy { it.games.value }
            GeneralRankingSortType.Wins -> elements.sortedBy { it.wins.value }
        }
        return if (reversed) list.reversed() else list
    }

    private fun selectStatFilterType(type: StatFilterType) {
        _state.update { state ->
            state.copy(
                selectedStatFilterType = type
            )
        }
    }

    private fun selectRankingFilterType(type: RankingFilterType) {
        _state.update { state ->
            state.copy(
                selectedRankingFilterType = type
            )
        }
    }

    private fun selectRankingModalType(modalType: RankingModalType?) {
        _state.update { state ->
            state.copy(modalType = modalType)
        }
    }

    private fun togglePlayerFavorites(brawlhallaId: Long, name: String) {
        viewModelScope.launch {
            if (_state.value.isStatDetailFavorite) {
                database.deletePlayer(brawlhallaId)
                _state.update { state -> state.copy(isStatDetailFavorite = false) }
                _uiEvents.send(UiEvent.Message(RankingMessage.Removed(name)))
            } else {
                database.savePlayer(
                    Player(
                        id = brawlhallaId,
                        name = name,
                        order = 0
                    )
                )
                _state.update { state -> state.copy(isStatDetailFavorite = true) }
                _uiEvents.send(UiEvent.Message(RankingMessage.Saved(name)))
            }

        }
    }

}