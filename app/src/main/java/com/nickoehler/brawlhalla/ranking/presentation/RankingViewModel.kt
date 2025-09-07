package com.nickoehler.brawlhalla.ranking.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickoehler.brawlhalla.core.domain.util.onError
import com.nickoehler.brawlhalla.core.domain.util.onSuccess
import com.nickoehler.brawlhalla.core.presentation.UiEvent
import com.nickoehler.brawlhalla.ranking.domain.Bracket
import com.nickoehler.brawlhalla.ranking.domain.RankingMessage
import com.nickoehler.brawlhalla.ranking.domain.RankingsDataSource
import com.nickoehler.brawlhalla.ranking.domain.Region
import com.nickoehler.brawlhalla.ranking.presentation.models.toRankingUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// https://dev.brawlhalla.com/#version-08
// rankings endpoint has a page limit of 1000 (50k players).
const val MAX_PAGE = 1_000

class RankingViewModel(
    private val rankingsDataSource: RankingsDataSource,
) : ViewModel() {
    private val _state = MutableStateFlow(RankingState())
    private var currentPage = 1
    val state = _state.onStart {
        if (_state.value.players.isEmpty()) loadRankings()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000L),
        RankingState()
    )

    private val _uiEvents = Channel<UiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    fun onRankingAction(action: RankingAction) {
        when (action) {
            is RankingAction.LoadMore -> loadMore()
            is RankingAction.SelectRegion -> selectRegion(action.region)
            is RankingAction.SelectBracket -> selectBracket(action.bracket)
            is RankingAction.QueryChange -> updateSearchQuery(action.query)
            is RankingAction.OnFilterToggle -> onFilterToggle()
            is RankingAction.Search -> search(_state.value.searchQuery)
            is RankingAction.SelectRanking -> selectRanking(action.brawlhallaId)
            is RankingAction.ResetSearch -> removeSearch()
        }
    }

    private fun loadRankings(shouldResetPlayers: Boolean = false) {
        if (shouldResetPlayers) {
            currentPage = 1
        }
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isListLoading = currentPage == 1,
                    isLoadingMore = currentPage != 1
                )
            }
            rankingsDataSource.getRankings(
                _state.value.selectedBracket,
                _state.value.selectedRegion,
                currentPage,
            ).onSuccess { players ->
                _state.update { state ->
                    state.copy(
                        shouldLoadMore = players.isNotEmpty(),
                        isListLoading = false,
                        isLoadingMore = false,
                        players = (if (shouldResetPlayers)
                            emptyList()
                        else
                            _state.value.players) +
                                players.map { it.toRankingUi() }
                    )
                }
            }.onError { error ->
                _state.update { state ->
                    state.copy(
                        isListLoading = false,
                        isLoadingMore = false
                    )
                }
                _uiEvents.send(UiEvent.Error(error))
            }
        }
    }

    private fun search(currentQuery: String) {
        val currentBracket = _state.value.selectedBracket
        val currentRegion = _state.value.selectedRegion

        val searchPlayers = {
            viewModelScope.launch {
                _state.update { state ->
                    state.copy(
                        searchedQuery = currentQuery,
                        searchResults = emptyList(),
                        isListLoading = true,
                    )
                }
                rankingsDataSource.getRankings(
                    currentBracket,
                    currentRegion,
                    1,
                    currentQuery
                ).onSuccess { rankings ->
                    _state.update { state ->
                        state.copy(
                            searchResults = rankings.map { it.toRankingUi() },
                            isListLoading = false,
                            searchQuery = ""
                        )
                    }
                    if (rankings.isEmpty()) {
                        _uiEvents.send(UiEvent.Message(RankingMessage.NoResult))
                    }
                }.onError {
                    _state.update { state ->
                        state.copy(
                            isListLoading = false,
                            searchQuery = ""
                        )
                    }
                    _uiEvents.send(UiEvent.Error(it))
                }
            }
        }
        
        if (!currentQuery.all { it.isDigit() }) {
            searchPlayers()
        }
    }


    private fun selectRegion(region: Region) {
        if (region == _state.value.selectedRegion) {
            return
        }
        _state.update { state ->
            state.copy(selectedRegion = region)
        }
        if (_state.value.searchedQuery.isNotBlank()) {
            search(_state.value.searchedQuery)
        } else {
            loadRankings(true)
        }

    }

    private fun selectBracket(bracket: Bracket) {
        if (bracket == _state.value.selectedBracket) {
            return
        }
        _state.update { state ->
            state.copy(selectedBracket = bracket)
        }
        if (_state.value.searchedQuery.isNotBlank()) {
            search(_state.value.searchedQuery)
        } else {
            loadRankings(true)
        }
    }

    private fun updateSearchQuery(query: String) {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    searchQuery = query,
                )
            }
        }
    }

    private fun onFilterToggle() {
        viewModelScope.launch {
            _uiEvents.send(UiEvent.ScrollToTop)
            _state.update { state ->
                state.copy(
                    isFilterOpen = !state.isFilterOpen,
                )
            }
        }
    }

    private fun removeSearch() {
        _state.update {
            it.copy(
                searchQuery = "",
                searchedQuery = "",
                searchResults = emptyList()
            )
        }
        loadRankings(true)
    }

    private fun selectRanking(brawlhallaId: Int) {
        viewModelScope.launch {
            _uiEvents.send(UiEvent.GoToDetail(brawlhallaId))
        }
    }

    private fun loadMore() {
        if (currentPage <= MAX_PAGE) {
            currentPage++
            loadRankings()
        }
    }
}