package com.nickoehler.brawlhalla.ranking.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickoehler.brawlhalla.core.domain.util.onError
import com.nickoehler.brawlhalla.core.domain.util.onSuccess
import com.nickoehler.brawlhalla.core.presentation.AppBarAction
import com.nickoehler.brawlhalla.core.presentation.CustomAppBarState
import com.nickoehler.brawlhalla.core.presentation.ErrorEvent
import com.nickoehler.brawlhalla.ranking.domain.RankingsDataSource
import com.nickoehler.brawlhalla.ranking.presentation.models.BracketUi
import com.nickoehler.brawlhalla.ranking.presentation.models.RegionUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toRankingUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toStatDetailUi
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

    private val _events = Channel<ErrorEvent>()
    val events = _events.receiveAsFlow()


    private fun loadRankings(shouldResetPlayers: Boolean = false) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isListLoading = currentPage == 1,
                    isLoadingMore = currentPage != 1
                )
            }
            rankingsDataSource.getRankings(
                _state.value.selectedBracket.value,
                _state.value.selectedRegion.value,
                currentPage,
                currentSearch
            ).onSuccess { players ->
                _state.update { state ->
                    state.copy(
                        shouldLoadMore = currentSearch == null && players.isNotEmpty(),
                        isListLoading = false,
                        isLoadingMore = false,
                        appBarState = _state.value.appBarState.copy(
                            searchQuery = if (currentSearch == null) "" else currentSearch!!,
                        ),
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
                _events.send(ErrorEvent.Error(error))
            }
        }
    }


    private fun selectRanking(id: Int) {

        if (_state.value.selectedRanking?.brawlhallaId == id) {
            return
        }

        _state.update { state ->
            state.copy(isDetailLoading = true)
        }

        viewModelScope.launch {
            rankingsDataSource.getStat(id).onSuccess { stat ->
                _state.update { state ->
                    state.copy(
                        isDetailLoading = false,
                        selectedRanking = stat.toStatDetailUi()
                    )
                }
            }.onError { error ->
                _state.update { state ->
                    state.copy(
                        isDetailLoading = false,
                    )
                }
                _events.send(ErrorEvent.Error(error))
            }
        }
    }

    private fun selectRegion(region: RegionUi) {
        if (region == _state.value.selectedRegion) {
            return
        }
        _state.update { state ->
            state.copy(selectedRegion = region)
        }
        resetSearch()
    }

    private fun selectBracket(bracket: BracketUi) {
        if (bracket == _state.value.selectedBracket) {
            return
        }
        _state.update { state ->
            state.copy(selectedBracket = bracket)
        }
        resetSearch()
    }

    private fun updateSearchQuery(query: String) {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    appBarState = CustomAppBarState(
                        searchQuery = query,
                        isOpenSearch = true
                    )
                )
            }
        }
    }

    private fun search() {
        currentPage = 1
        currentSearch = _state.value.appBarState.searchQuery
        loadRankings(true)
    }

    private fun resetSearch() {
        currentPage = 1
        currentSearch = null
        loadRankings(true)
        _state.update { state ->
            state.copy(
                appBarState = CustomAppBarState(
                    isOpenSearch = false,
                    searchQuery = ""
                )
            )
        }
    }


    private fun openSearch() {
        _state.update { state ->
            state.copy(appBarState = CustomAppBarState(isOpenSearch = true))
        }
    }

    fun onRankingAction(action: RankingAction) {
        when (action) {
            is RankingAction.LoadMore -> {
                if (currentPage <= MAX_PAGE) {
                    currentPage++
                    loadRankings()
                }
            }

            is RankingAction.SelectRanking -> selectRanking(action.id)
            is RankingAction.SelectBracket -> selectBracket(action.bracket)
            is RankingAction.SelectRegion -> selectRegion(action.region)
        }
    }

    fun onAppBarAction(action: AppBarAction) {
        when (action) {
            is AppBarAction.CloseSearch -> resetSearch()
            is AppBarAction.OpenSearch -> openSearch()
            is AppBarAction.QueryChange -> updateSearchQuery(action.query)
            is AppBarAction.Search -> search()
        }
    }
}