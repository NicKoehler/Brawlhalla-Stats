package com.nickoehler.brawlhalla.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickoehler.brawlhalla.core.data.database.entities.Clan
import com.nickoehler.brawlhalla.core.data.database.entities.Player
import com.nickoehler.brawlhalla.core.domain.LocalDataSource
import com.nickoehler.brawlhalla.core.presentation.UiEvent
import com.nickoehler.brawlhalla.favorites.FavoriteAction
import com.nickoehler.brawlhalla.favorites.presentation.model.FavoriteType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class FavoritesViewModel(
    private val database: LocalDataSource
) : ViewModel() {

    private val _players = MutableStateFlow<List<Player>>(emptyList())
    val players: StateFlow<List<Player>> = _players.asStateFlow()

    private val _clans = MutableStateFlow<List<Clan>>(emptyList())
    val clans: StateFlow<List<Clan>> = _clans.asStateFlow()

    private val _state = MutableStateFlow(FavoritesState())
    val state: StateFlow<FavoritesState> = _state.asStateFlow()

    private val _uiEvents = Channel<UiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    init {
        loadPlayers()
        loadClans()

        combine(_players, _clans) { players, clans ->
            _state.update { state ->
                state.copy(
                    players = players,
                    clans = clans,
                    selectedFavoriteType =
                    if (players.isNotEmpty()) {
                        FavoriteType.PLAYERS
                    } else if (clans.isNotEmpty()) {
                        FavoriteType.CLANS
                    } else {
                        null
                    }
                )
            }
        }.launchIn(viewModelScope)

        if (_players.value.isNotEmpty()) {
            _state
        }
    }

    private fun loadPlayers() {
        viewModelScope.launch {
            database.getAllPlayers()
                .collect { playerList ->
                    _players.value = playerList
                }
        }
    }

    private fun loadClans() {
        viewModelScope.launch {
            database.getAllClans()
                .collect { clanList ->
                    _clans.value = clanList
                }
        }
    }


    private fun selectFavorite(fav: FavoriteType) {
        _state.value = _state.value.copy(selectedFavoriteType = fav)
    }

    fun onFavoriteAction(action: FavoriteAction) {
        when (action) {
            is FavoriteAction.SelectFavorite -> selectFavorite(action.fav)
            else -> {}
        }
    }
}
