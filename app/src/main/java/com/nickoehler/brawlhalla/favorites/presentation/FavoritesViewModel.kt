package com.nickoehler.brawlhalla.favorites.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickoehler.brawlhalla.core.data.database.entities.Clan
import com.nickoehler.brawlhalla.core.data.database.entities.Player
import com.nickoehler.brawlhalla.core.domain.LocalDataSource
import com.nickoehler.brawlhalla.core.presentation.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn


class FavoritesViewModel(
    private val database: LocalDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(FavoritesState())
    val state = _state.onStart {
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        FavoritesState()
    )

    private val _uiEvents = Channel<UiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    fun getPlayerLiveData(): LiveData<List<Player>> {
        return database.getAllPlayers()
    }

    fun getClanLiveData(): LiveData<List<Clan>> {
        return database.getAllClans()
    }

}