package com.nickoehler.brawlhalla.legends.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickoehler.brawlhalla.legends.presentation.models.LegendDetailUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class LegendDetailViewModel(
    private val legendDetail: LegendDetailUi
) : ViewModel() {
    private val _state = MutableStateFlow(LegendDetailState())
    val state = _state.onStart {
        _state.update { state ->
            state.copy(selectedLegendUi = legendDetail)
        }
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            LegendDetailState()
        )
}