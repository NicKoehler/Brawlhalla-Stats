package com.nickoehler.brawlhalla.guilds.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickoehler.brawlhalla.core.data.database.entities.Guild
import com.nickoehler.brawlhalla.core.domain.LocalDataSource
import com.nickoehler.brawlhalla.core.domain.util.onError
import com.nickoehler.brawlhalla.core.domain.util.onSuccess
import com.nickoehler.brawlhalla.core.presentation.UiEvent
import com.nickoehler.brawlhalla.guilds.domain.GuildDataSource
import com.nickoehler.brawlhalla.guilds.presentation.model.GuildMemberUi
import com.nickoehler.brawlhalla.guilds.presentation.model.GuildSortType
import com.nickoehler.brawlhalla.guilds.presentation.model.toGuildDetailUi
import com.nickoehler.brawlhalla.ranking.domain.RankingMessage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class GuildViewModel(
    private val guildId: Long,
    private val guildDataSource: GuildDataSource,
    private val database: LocalDataSource,
) : ViewModel() {
    private val _state = MutableStateFlow(GuildState())
    val state = _state.onStart { selectGuild(guildId) }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        GuildState()
    )

    private val _uiEvents = Channel<UiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    private fun selectGuild(guildId: Long) {

        if (_state.value.selectedGuild?.id == guildId) {
            return
        }

        _state.update { state -> state.copy(isGuildDetailLoading = true, error = null) }

        viewModelScope.launch {
            guildDataSource.getGuild(guildId).onSuccess { guildDetail ->
                database.getGuild(guildId).collect { guild ->
                    _state.update { state ->
                        val guildDetailUi = guildDetail.toGuildDetailUi()
                        state.copy(
                            selectedGuild = guildDetailUi.copy(
                                members = sortMembers(
                                    state.sortType,
                                    guildDetailUi.members
                                )
                            ),
                            isGuildDetailLoading = false,
                            isGuildDetailFavorite = guild != null
                        )
                    }
                }
            }.onError { error ->
                _state.update { state ->
                    state.copy(
                        isGuildDetailLoading = false,
                        error = error
                    )
                }
            }
        }
    }

    fun onGuildAction(action: GuildAction) {
        when (action) {
            GuildAction.ReverseSortType -> reverseSortType()
            is GuildAction.SelectGuild -> selectGuild(action.guildId)
            is GuildAction.ToggleGuildFavorites -> toggleGuildFavorites(
                action.guildId,
                action.name
            )

            is GuildAction.SelectSortType -> selectSortType(action.sort)
            else -> {}
        }
    }

    private fun reverseSortType() {
        _state.update { state ->
            val reversed = !state.reversedSortType
            state.copy(
                reversedSortType = reversed,
                selectedGuild = state.selectedGuild?.copy(
                    members = state.selectedGuild.members.reversed()
                )
            )
        }
    }


    private fun selectSortType(sort: GuildSortType) {
        val currentGuild = state.value.selectedGuild
        if (currentGuild != null) {
            _state.update { state ->
                state.copy(
                    sortType = sort,
                    selectedGuild = currentGuild.copy(
                        members = sortMembers(sort, currentGuild.members)
                    )
                )
            }
        }
    }

    private fun sortMembers(
        sort: GuildSortType,
        members: List<GuildMemberUi>
    ): List<GuildMemberUi> {
        val reversed = state.value.reversedSortType

        val result = when (sort) {
            GuildSortType.Alpha -> members.sortedBy { it.name }
            GuildSortType.JoinDate -> members.sortedBy { it.joinDate.value }
            GuildSortType.Rank -> members.sortedBy { it.rank.name }
            GuildSortType.Xp -> members.sortedBy { it.xp.value }
        }
        return if (reversed) result.reversed() else result
    }


    private fun toggleGuildFavorites(guildId: Long, name: String) {
        viewModelScope.launch {
            if (_state.value.isGuildDetailFavorite) {
                database.deleteGuild(guildId)
                _state.update { state -> state.copy(isGuildDetailFavorite = false) }
                _uiEvents.send(UiEvent.Message(RankingMessage.Removed(name)))
            } else {
                database.saveGuild(
                    Guild(
                        id = guildId,
                        name = name,
                        order = 0
                    )
                )
                _state.update { state -> state.copy(isGuildDetailFavorite = true) }
                _uiEvents.send(UiEvent.Message(RankingMessage.Saved(name)))
            }

        }
    }
}