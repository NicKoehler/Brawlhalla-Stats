package com.nickoehler.brawlhalla.legends.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickoehler.brawlhalla.core.domain.util.onError
import com.nickoehler.brawlhalla.core.domain.util.onSuccess
import com.nickoehler.brawlhalla.core.presentation.UiEvent
import com.nickoehler.brawlhalla.legends.domain.LegendsDataSource
import com.nickoehler.brawlhalla.legends.presentation.models.toLegendDetailUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LegendDetailViewModel(
    private val legendId: Long,
    private val legendsDataSource: LegendsDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(LegendDetailState())
    val state = _state.onStart { selectLegend(legendId) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            LegendDetailState()
        )

    private val _uiEvents = Channel<UiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    private fun selectLegend(legendId: Long) {
        viewModelScope.launch {
            if (_state.value.selectedLegendUi?.legendId != legendId) {
                _state.update { it.copy(isDetailLoading = true, selectedLegendUi = null) }
                legendsDataSource.getLegendDetail(legendId).onSuccess { legend ->
                    _state.update { state ->
                        state.copy(
                            isDetailLoading = false,
                            selectedLegendUi = legend.toLegendDetailUi()
                        )
                    }
                }.onError { error ->
                    _state.update { it.copy(isDetailLoading = false, selectedLegendUi = null) }
                    _uiEvents.send(UiEvent.Error(error))
                }
            }
        }
    }
}