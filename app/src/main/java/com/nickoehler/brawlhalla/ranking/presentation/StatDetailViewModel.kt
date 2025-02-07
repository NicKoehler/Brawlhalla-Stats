package com.nickoehler.brawlhalla.ranking.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickoehler.brawlhalla.core.domain.LocalDataSource
import com.nickoehler.brawlhalla.core.domain.util.onError
import com.nickoehler.brawlhalla.core.domain.util.onSuccess
import com.nickoehler.brawlhalla.core.presentation.UiEvent
import com.nickoehler.brawlhalla.ranking.domain.RankingMessage
import com.nickoehler.brawlhalla.ranking.domain.RankingsDataSource
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
    private val brawlhallaId: Int,
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

    private fun selectStatDetail(id: Int) {
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
                        state.copy(
                            isStatDetailFavorite = player != null,
                            isStatDetailLoading = false,
                            selectedStatDetail = stat.toStatDetailUi()
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

    private fun selectRankingDetail(id: Int) {
        if (_state.value.selectedRankingDetail?.brawlhallaId == id) {
            return
        }

        _state.update { state ->
            state.copy(isRankingDetailLoading = true, selectedRankingDetail = null)
        }

        viewModelScope.launch {
            rankingsDataSource.getRanked(id).onSuccess { stat ->
                _state.update { state ->
                    state.copy(
                        isRankingDetailLoading = false,
                        selectedRankingDetail = stat.toRankingDetailUi()
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
            is StatDetailAction.TogglePlayerFavorites -> togglePlayerFavorites(
                action.brawlhallaId,
                action.name
            )

            else -> {}
        }
    }

    private fun togglePlayerFavorites(brawlhallaId: Int, name: String) {
        viewModelScope.launch {
            if (_state.value.isStatDetailFavorite) {
                database.deletePlayer(brawlhallaId)
                _state.update { state -> state.copy(isStatDetailFavorite = false) }
                _uiEvents.send(UiEvent.Message(RankingMessage.Removed(name)))
            } else {
                database.savePlayer(
                    brawlhallaId,
                    name
                )
                _state.update { state -> state.copy(isStatDetailFavorite = true) }
                _uiEvents.send(UiEvent.Message(RankingMessage.Saved(name)))
            }

        }
    }

}