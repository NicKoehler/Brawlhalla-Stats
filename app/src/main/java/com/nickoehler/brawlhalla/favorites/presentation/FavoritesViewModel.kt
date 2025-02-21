package com.nickoehler.brawlhalla.favorites.presentation

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickoehler.brawlhalla.core.domain.LocalDataSource
import com.nickoehler.brawlhalla.favorites.FavoriteAction
import com.nickoehler.brawlhalla.favorites.presentation.model.FavoriteType
import com.nickoehler.brawlhalla.widgets.FavoriteWidget
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

    private fun loadData() {
        viewModelScope.launch {
            combine(database.getAllPlayers(), database.getAllClans()) { players, clans ->
                val glanceIds =
                    GlanceAppWidgetManager(context).getGlanceIds(FavoriteWidget::class.java)
                glanceIds.forEach { id ->
                    updateAppWidgetState(context, id) { prefs ->
                        prefs[stringPreferencesKey("players")] = Json.encodeToString(players)
                        prefs[stringPreferencesKey("clans")] = Json.encodeToString(clans)
                    }
                    FavoriteWidget().update(context, id)
                }
                _state.value.copy(
                    players = players.sortedBy { it.name.lowercase() },
                    clans = clans.sortedBy { it.name },
                    selectedFavoriteType =
                    if (players.isNotEmpty()) {
                        FavoriteType.Players
                    } else if (clans.isNotEmpty()) {
                        FavoriteType.Clans
                    } else {
                        null
                    }
                )
            }.collect { state ->

                _state.update {
                    state
                }
            }
        }
    }


    private fun selectFavorite(fav: FavoriteType) {
        _state.update { state ->
            state.copy(selectedFavoriteType = fav)
        }
    }

    private fun deletePlayer(brawlhallaId: Int) {
        viewModelScope.launch {
            database.deletePlayer(brawlhallaId)
        }
    }

    private fun deleteClan(clanId: Int) {
        viewModelScope.launch {
            database.deleteClan(clanId)
        }
    }

    fun onFavoriteAction(action: FavoriteAction) {
        when (action) {
            is FavoriteAction.SelectFavorite -> selectFavorite(action.fav)
            is FavoriteAction.DeletePlayer -> deletePlayer(action.brawlhallaId)
            is FavoriteAction.DeleteClan -> deleteClan(action.clanId)
            else -> {}
        }
    }
}
