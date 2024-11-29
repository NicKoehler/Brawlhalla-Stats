package com.nickoehler.brawlhalla.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickoehler.brawlhalla.core.domain.util.onError
import com.nickoehler.brawlhalla.core.domain.util.onSuccess
import com.nickoehler.brawlhalla.search.domain.RankingsDataSource
import com.nickoehler.brawlhalla.search.presentation.models.toRankingUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RankingViewModel(
    private val rankingsDataSource: RankingsDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(RankingState())
    private var currentPage = 1
    private var currentSearch: String? = null
    val state = _state.onStart {
        if (_state.value.players.isEmpty()) loadRankings()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        RankingState()
    )

    private fun loadRankings(shouldResetPlayers: Boolean = false, page: Int = 1) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isListLoading = page == 1,
                    isLoadingMore = page != 1
                )
            }
            rankingsDataSource.getRankings(
                _state.value.selectedBracket,
                _state.value.selectedRegion,
                page,
                currentSearch
            ).onSuccess { players ->
                _state.update { state ->
                    state.copy(
                        showLoadMore = currentSearch == null,
                        isListLoading = false,
                        isLoadingMore = false,
                        searchQuery = if (currentSearch == null) "" else currentSearch!!,
                        players = (if (shouldResetPlayers)
                            emptyList()
                        else
                            _state.value.players) +
                                players.map { it.toRankingUi() }


                    )
                }
            }.onError {
                _state.update { state ->
                    state.copy(
                        isListLoading = false,
                        isLoadingMore = false
                    )
                }
            }
        }
    }

    private fun updateSearchQuery(query: String) {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(searchQuery = query)
            }
        }
    }

    private fun search() {
        currentPage = 1
        currentSearch = _state.value.searchQuery
        loadRankings(true)
    }

    private fun resetSearch() {
        if (currentSearch != null) {
            currentPage = 1
            currentSearch = null
            loadRankings(true)
        } else {
            _state.update { state ->
                state.copy(searchQuery = "")
            }
        }
    }


    fun onRankingAction(action: RankingAction) {
        when (action) {
            is RankingAction.LoadMore -> loadRankings(page = ++currentPage)
            is RankingAction.SearchQuery -> updateSearchQuery(action.query)
            is RankingAction.Search -> search()
            is RankingAction.ResetSearch -> resetSearch()
        }
    }
}