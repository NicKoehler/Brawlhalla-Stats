package com.nickoehler.brawlhalla.clans.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickoehler.brawlhalla.clans.domain.ClanDataSource
import com.nickoehler.brawlhalla.core.domain.LocalDataSource
import com.nickoehler.brawlhalla.core.domain.util.onError
import com.nickoehler.brawlhalla.core.domain.util.onSuccess
import com.nickoehler.brawlhalla.core.presentation.UiEvent
import com.nickoehler.brawlhalla.ranking.domain.RankingMessage
import com.nickoehler.brawlhalla.ranking.presentation.models.toClanDetailUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ClanViewModel(
    private val clanId: Int,
    private val clanDataSource: ClanDataSource,
    private val database: LocalDataSource,
) : ViewModel() {
    private val _state = MutableStateFlow(ClanState())
    val state = _state.onStart { selectClan(clanId) }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        ClanState()
    )

    private val _uiEvents = Channel<UiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    private fun selectClan(clanId: Int) {

        if (_state.value.selectedClan?.id == clanId) {
            return
        }

        _state.update { state -> state.copy(isClanDetailLoading = true) }

        viewModelScope.launch {
            clanDataSource.getClan(clanId).onSuccess {
                database.getClan(clanId).collect { clan ->
                    _state.update { state ->
                        state.copy(
                            selectedClan = it.toClanDetailUi(),
                            isClanDetailLoading = false,
                            isClanDetailFavorite = clan != null
                        )
                    }
                }
            }.onError { error ->
                _state.update { state ->
                    state.copy(
                        isClanDetailLoading = false
                    )
                }
                _uiEvents.send(UiEvent.Error(error))
            }
        }
    }

    fun onClanAction(action: ClanAction) {
        when (action) {
            is ClanAction.SelectClan -> selectClan(action.clanId)
            is ClanAction.ToggleClanFavorites -> toggleClanFavorites(
                action.clanId,
                action.name
            )

            else -> {}
        }
    }


    private fun toggleClanFavorites(clanId: Int, name: String) {

        viewModelScope.launch {

            if (_state.value.isClanDetailFavorite) {
                database.deleteClan(clanId)
                _state.update { state -> state.copy(isClanDetailFavorite = false) }
                _uiEvents.send(UiEvent.Message(RankingMessage.Removed(name)))
            } else {
                database.saveClan(
                    clanId,
                    name
                )
                _state.update { state -> state.copy(isClanDetailFavorite = true) }
                _uiEvents.send(UiEvent.Message(RankingMessage.Saved(name)))
            }

        }
    }
}