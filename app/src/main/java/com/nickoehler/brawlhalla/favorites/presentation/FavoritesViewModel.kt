package com.nickoehler.brawlhalla.favorites.presentation

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickoehler.brawlhalla.core.data.database.entities.Guild
import com.nickoehler.brawlhalla.core.data.database.entities.Player
import com.nickoehler.brawlhalla.core.domain.LocalDataSource
import com.nickoehler.brawlhalla.favorites.presentation.model.FavoriteAction
import com.nickoehler.brawlhalla.favorites.presentation.model.FavoriteType
import com.nickoehler.brawlhalla.favorites.presentation.model.FavoritesState
import com.nickoehler.brawlhalla.widgets.GuildsWidget
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
    private val database: LocalDataSource
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
            is FavoriteAction.DeleteGuild -> deleteGuild(action.guildId)
            is FavoriteAction.RestorePlayer -> restorePlayer(action.player)
            is FavoriteAction.RestoreGuild -> restoreGuild(action.guild)
            is FavoriteAction.PlayerDragged -> playerDragged(action.fromIndex, action.toIndex)
            is FavoriteAction.GuildDragged -> guildDragged(action.fromIndex, action.toIndex)
            is FavoriteAction.PersistPlayers -> persistPlayers()
            is FavoriteAction.PersistGuilds -> persistGuilds()
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

    private fun persistGuilds() {
        viewModelScope.launch {
            database.updateGuilds(state.value.guilds)
        }
    }

    private fun guildDragged(fromIndex: Int, toIndex: Int) {
        _state.update { state ->
            state.copy(
                guilds = state.guilds.toMutableList()
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

    private fun restoreGuild(guild: Guild) {
        viewModelScope.launch {
            database.saveGuild(guild)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            combine(database.getAllPlayers(), database.getAllGuilds())
            { players, guilds ->
                val favoriteType = if (_state.value.selectedFavoriteType == null) {
                    if (players.isNotEmpty()) {
                        FavoriteType.Players
                    } else if (guilds.isNotEmpty()) {
                        FavoriteType.Guilds
                    } else {
                        null
                    }
                } else {
                    _state.value.selectedFavoriteType
                }
                _state.value.copy(
                    players = players,
                    guilds = guilds,
                    selectedFavoriteType = favoriteType
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

    private fun deletePlayer(brawlhallaId: Long) {
        viewModelScope.launch {
            database.deletePlayer(brawlhallaId)
        }
    }

    private fun deleteGuild(guildId: Long) {
        viewModelScope.launch {
            database.deleteGuild(guildId)
        }
    }

}
