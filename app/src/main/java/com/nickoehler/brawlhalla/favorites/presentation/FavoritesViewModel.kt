package com.nickoehler.brawlhalla.favorites.presentation

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickoehler.brawlhalla.core.data.database.entities.Clan
import com.nickoehler.brawlhalla.core.data.database.entities.Player
import com.nickoehler.brawlhalla.core.domain.LocalDataSource
import com.nickoehler.brawlhalla.favorites.presentation.model.FavoriteAction
import com.nickoehler.brawlhalla.favorites.presentation.model.FavoriteType
import com.nickoehler.brawlhalla.favorites.presentation.model.FavoritesState
import com.nickoehler.brawlhalla.widgets.ClansWidget
import com.nickoehler.brawlhalla.widgets.PlayersWidget
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@SuppressLint("StaticFieldLeak")
class FavoritesViewModel(
    private val database: LocalDataSource,
    private val context: Context
) : ViewModel() {
    private val _state = MutableStateFlow(FavoritesState())
    val state = _state.onStart {
        loadData()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000L),
        FavoritesState()
    )

    fun onFavoriteAction(action: FavoriteAction) {
        when (action) {
            is FavoriteAction.SelectFavorite -> selectFavorite(action.fav)
            is FavoriteAction.DeletePlayer -> deletePlayer(action.brawlhallaId)
            is FavoriteAction.DeleteClan -> deleteClan(action.clanId)
            is FavoriteAction.RestorePlayer -> restorePlayer(action.player)
            is FavoriteAction.RestoreClan -> restoreClan(action.clan)
            is FavoriteAction.PlayerDragged -> playerDragged(action.fromIndex, action.toIndex)
            is FavoriteAction.ClanDragged -> clanDragged(action.fromIndex, action.toIndex)
            is FavoriteAction.PersistPlayers -> persistPlayers()
            is FavoriteAction.PersistClans -> persistClans()
            else -> Unit
        }
    }

    private fun persistPlayers() {
        viewModelScope.launch {
            database.updatePlayers(state.value.players)
        }
    }

    private fun playerDragged(fromIndex: Int, toIndex: Int) {
        _state.update { state ->
            state.copy(
                players = state.players.toMutableList()
                    .apply {
                        add(toIndex, removeAt(fromIndex))
                    }
            )
        }
    }

    private fun persistClans() {
        viewModelScope.launch {
            database.updateClans(state.value.clans)
        }
    }

    private fun clanDragged(fromIndex: Int, toIndex: Int) {
        _state.update { state ->
            state.copy(
                clans = state.clans.toMutableList()
                    .apply {
                        add(toIndex, removeAt(fromIndex))
                    }
            )
        }
    }


    private fun restorePlayer(player: Player) {
        viewModelScope.launch {
            database.savePlayer(player)
        }
    }

    private fun restoreClan(clan: Clan) {
        viewModelScope.launch {
            database.saveClan(clan)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            combine(database.getAllPlayers(), database.getAllClans())
            { players, clans ->
                updateWidgetState(players, clans)
                val favoriteType = if (_state.value.selectedFavoriteType == null) {
                    if (players.isNotEmpty()) {
                        FavoriteType.Players
                    } else if (clans.isNotEmpty()) {
                        FavoriteType.Clans
                    } else {
                        null
                    }
                } else {
                    _state.value.selectedFavoriteType
                }
                _state.value.copy(
                    players = players,
                    clans = clans,
                    selectedFavoriteType = favoriteType
                )
            }.collect { state ->
                _state.update {
                    state
                }
            }
        }
    }

    private suspend fun updateWidgetState(
        players: List<Player>,
        clans: List<Clan>
    ) {
        val playersGlanceIds = GlanceAppWidgetManager(context)
            .getGlanceIds(PlayersWidget::class.java)
        val clansGlanceIds = GlanceAppWidgetManager(context)
            .getGlanceIds(ClansWidget::class.java)
        playersGlanceIds.forEach { id ->
            updateAppWidgetState(context, id) { prefs ->
                prefs[stringPreferencesKey("players")] = Json.encodeToString(players)
            }
            PlayersWidget().update(context, id)
        }

        clansGlanceIds.forEach { id ->
            updateAppWidgetState(context, id) { prefs ->
                prefs[stringPreferencesKey("clans")] = Json.encodeToString(clans)
            }
            ClansWidget().update(context, id)
        }
    }

    private fun selectFavorite(fav: FavoriteType) {
        _state.update { state ->
            state.copy(selectedFavoriteType = fav)
        }
    }

    private fun deletePlayer(brawlhallaId: Long) {
        viewModelScope.launch {
            database.deletePlayer(brawlhallaId)
        }
    }

    private fun deleteClan(clanId: Long) {
        viewModelScope.launch {
            database.deleteClan(clanId)
        }
    }

}
