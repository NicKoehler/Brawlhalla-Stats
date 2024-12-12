package com.nickoehler.brawlhalla.ranking.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickoehler.brawlhalla.core.domain.LocalDataSource
import com.nickoehler.brawlhalla.core.domain.util.onError
import com.nickoehler.brawlhalla.core.domain.util.onSuccess
import com.nickoehler.brawlhalla.core.presentation.AppBarAction
import com.nickoehler.brawlhalla.core.presentation.CustomAppBarState
import com.nickoehler.brawlhalla.core.presentation.UiEvent
import com.nickoehler.brawlhalla.ranking.domain.Bracket
import com.nickoehler.brawlhalla.ranking.domain.RankingMessage
import com.nickoehler.brawlhalla.ranking.domain.RankingsDataSource
import com.nickoehler.brawlhalla.ranking.domain.Region
import com.nickoehler.brawlhalla.ranking.presentation.models.StatType
import com.nickoehler.brawlhalla.ranking.presentation.models.toRankingDetailUi
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
    private val rankingsDataSource: RankingsDataSource,
    private val database: LocalDataSource,
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

    private val _uiEvents = Channel<UiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    private fun loadRankings(shouldResetPlayers: Boolean = false) {
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
                _uiEvents.send(UiEvent.Error(error))
            }
        }
    }


    private fun selectStatDetail(id: Int) {

        if (_state.value.selectedStatDetail?.brawlhallaId == id) {
            return
        }

        _state.update { state ->
            state.copy(
                rankingEnabled = true,
                selectedStatDetail = null,
                isStatDetailLoading = true,
                selectedStatType = StatType.General
            )
        }

        viewModelScope.launch {
            rankingsDataSource.getStat(id).onSuccess { stat ->
                database.getPlayer(brawlhallaId = id)
                    .collect { player ->
                        _state.update { state ->
                            state.copy(
                                isStatDetailFavorite = player != null,
                                isStatDetailLoading = false,
                                selectedStatDetail = stat.toStatDetailUi()
                            )
                        }
                    }
            }.onError { error ->
                _state.update { state ->
                    state.copy(
                        isStatDetailLoading = false,
                    )
                }
                _uiEvents.send(UiEvent.Error(error))
            }
        }
    }

    private fun selectRankingDetail(id: Int) {

        if (_state.value.selectedRankingDetail?.brawlhallaId == id) {
            return
        }

        _state.update { state ->
            state.copy(isRankingDetailLoading = true, selectedRankingDetail = null)
        }

        viewModelScope.launch {
            rankingsDataSource.getRanked(id).onSuccess { stat ->
                _state.update { state ->
                    state.copy(
                        isRankingDetailLoading = false,
                        selectedRankingDetail = stat.toRankingDetailUi()
                    )
                }
            }.onError { error ->
                _state.update { state ->
                    state.copy(
                        isRankingDetailLoading = false,
                        rankingEnabled = false,
                        selectedStatType = StatType.General
                    )
                }
                _uiEvents.send(UiEvent.Error(error))
            }
        }
    }

    private fun selectRegion(region: Region) {
        if (region == _state.value.selectedRegion) {
            return
        }
        _state.update { state ->
            state.copy(selectedRegion = region)
        }
        resetSearch()
    }

    private fun selectBracket(bracket: Bracket) {
        if (bracket == _state.value.selectedBracket) {
            return
        }
        _state.update { state ->
            state.copy(selectedBracket = bracket)
        }
        resetSearch()
    }

    private fun selectStatType(stat: StatType) {
        if (stat == StatType.Ranking && _state.value.selectedStatDetail != null) {
            selectRankingDetail(_state.value.selectedStatDetail!!.brawlhallaId)
        }

        _state.update { state ->
            state.copy(selectedStatType = stat)
        }
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
        val currentQuery = _state.value.appBarState.searchQuery
        currentPage = 1
        currentSearch = currentQuery

        if (currentQuery.all { it.isDigit() }) {
            try {
                val id = currentQuery.toInt()
                viewModelScope.launch {
                    _uiEvents.send(UiEvent.GoToDetail)
                }
                selectStatDetail(id)
            } catch (e: NumberFormatException) {
                loadRankings(true)
            }
        } else {
            loadRankings(true)
        }
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

            is RankingAction.SelectRanking -> selectStatDetail(action.brawlhallaId)
            is RankingAction.SelectBracket -> selectBracket(action.bracket)
            is RankingAction.SelectRegion -> selectRegion(action.region)
            is RankingAction.SelectStatType -> selectStatType(action.stat)
            is RankingAction.TogglePlayerFavorites -> togglePlayerFavorites(
                action.brawlhallaId,
                action.name
            )

            else -> {}
        }
    }

    private fun togglePlayerFavorites(brawlhallaId: Int, name: String) {
        viewModelScope.launch {
            if (_state.value.isStatDetailFavorite) {
                database.deletePlayer(brawlhallaId)
                _state.update { state -> state.copy(isStatDetailFavorite = false) }
                _uiEvents.send(UiEvent.Message(RankingMessage.Removed(name)))
            } else {
                database.savePlayer(
                    brawlhallaId,
                    name
                )
                _state.update { state -> state.copy(isStatDetailFavorite = true) }
                _uiEvents.send(UiEvent.Message(RankingMessage.Saved(name)))
            }

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

    fun onRankingEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvents.send(event)
        }
    }
}