package com.nickoehler.brawlhalla.legends.presentation

import LegendsEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickoehler.brawlhalla.core.domain.util.onError
import com.nickoehler.brawlhalla.core.domain.util.onSuccess
import com.nickoehler.brawlhalla.core.presentation.WeaponAction
import com.nickoehler.brawlhalla.core.presentation.domain.WeaponUi
import com.nickoehler.brawlhalla.legends.domain.LegendsDataSource
import com.nickoehler.brawlhalla.legends.domain.Stat
import com.nickoehler.brawlhalla.legends.presentation.models.FilterOptions
import com.nickoehler.brawlhalla.legends.presentation.models.LegendUi
import com.nickoehler.brawlhalla.legends.presentation.models.getStat
import com.nickoehler.brawlhalla.legends.presentation.models.toLegendDetailUi
import com.nickoehler.brawlhalla.legends.presentation.models.toLegendUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LegendsViewModel(
    private val legendsDataSource: LegendsDataSource,
) : ViewModel() {
    private val _state = MutableStateFlow(LegendsListState())
    private var _legends: List<LegendUi> = emptyList()
    private var _weapons: List<WeaponUi> = emptyList()
    val state = _state.onStart { loadLegends() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            LegendsListState()
        )
    private val _events = Channel<LegendsEvent>()
    val events = _events.receiveAsFlow()

    private fun loadLegends() {
        viewModelScope.launch {
            _state.update { it.copy(isListLoading = true) }

            legendsDataSource.getLegends().onSuccess { legends ->
                _legends = legends.map { it.toLegendUi() }
                _weapons = getAllWeapons()

                _state.update { state ->
                    state.copy(
                        isListLoading = false,
                        legends = _legends,
                        weapons = _weapons
                    )
                }
            }.onError { error ->
                _state.update { it.copy(isListLoading = false) }
                _events.send(LegendsEvent.Error(error))
            }
        }
    }

    private fun selectLegend(legendId: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isDetailLoading = true) }
            legendsDataSource.getLegendDetail(legendId).onSuccess { legend ->
                _state.update { state ->
                    state.copy(
                        isDetailLoading = false,
                        selectedLegendUi = legend.toLegendDetailUi()
                    )
                }
            }.onError { error ->
                _state.update { it.copy(isDetailLoading = false) }
                _events.send(LegendsEvent.Error(error))
            }
        }
    }

    private fun applyFilters(query: String) {
        _state.update { state ->
            state.copy(
                searchQuery = query,
                legends = _legends.filter {
                    filterLegend(query, it)
                },
            )
        }
    }

    private fun filterLegend(query: String, legend: LegendUi): Boolean {
        if (query == "") {
            return false
        }
        return legend.legendNameKey.lowercase().contains(query.lowercase())
    }

    private fun toggleSearch(isOpen: Boolean) {
        _state.update { state ->
            state.copy(
                openSearch = isOpen,
                searchQuery = "",
                weapons = if (isOpen) emptyList() else _weapons,
                legends = if (isOpen) emptyList() else _legends
            )
        }
    }

    private fun toggleFilters() {
        _state.update { state ->
            val isOpening = !state.openFilters
            state.copy(
                openFilters = isOpening,
                selectedFilter = if (isOpening) FilterOptions.WEAPONS else state.selectedFilter,
                legends = if (!isOpening) _legends else state.legends,
                weapons = if (isOpening) _weapons else state.weapons,
            )
        }
    }

    private fun getAllWeapons(): List<WeaponUi> {
        return _legends.flatMap { legend ->
            listOf(legend.weaponOne, legend.weaponTwo)
        }.distinct().sortedBy { it.name }
    }

    private fun onWeaponSelect(weapon: WeaponUi) {
        _state.update { state ->
            val weaponState = state.weapons.map { w ->
                if (w == weapon) {
                    w.copy(selected = !w.selected)
                } else {
                    w
                }
            }

            val selected = weaponState.filter { it.selected }.map { it.name }

            val result = when (selected.size) {
                1 -> {
                    val filteredLegends = _legends.filter { legend ->
                        selected.any { selectedWeapon ->
                            legend.weaponOne.name == selectedWeapon || legend.weaponTwo.name == selectedWeapon
                        }
                    }

                    val filteredWeapons = _weapons.filter { weapon ->
                        filteredLegends.any { legend -> legend.weaponOne.name == weapon.name || legend.weaponTwo.name == weapon.name }
                    }.map { weapon ->
                        if (selected.contains(weapon.name)) {
                            weapon.copy(
                                selected = true
                            )
                        } else {
                            weapon
                        }
                    }.sortedBy { it.name }.sortedBy { !it.selected }
                    state.copy(
                        legends = filteredLegends,
                        weapons = filteredWeapons
                    )
                }

                2 -> {
                    val filteredLegends = _legends.filter { legend ->
                        (
                                legend.weaponOne.name == selected[0]
                                        && legend.weaponTwo.name == selected[1])
                                || (
                                legend.weaponOne.name == selected[1]
                                        && legend.weaponTwo.name == selected[0]
                                )
                    }
                    val filteredWeapons = weaponState.filter { weapon ->
                        filteredLegends.any { legend ->
                            legend.weaponOne.name == weapon.name
                                    || legend.weaponTwo.name == weapon.name
                        }
                    }.sortedBy { it.name }.sortedBy { !it.selected }

                    state.copy(
                        legends = filteredLegends,
                        weapons = filteredWeapons
                    )
                }

                else -> state.copy(
                    weapons = _weapons,
                    legends = _legends
                )

            }
            result.copy(
                openFilters = true,
                openSearch = false,
                selectedFilter = FilterOptions.WEAPONS
            )
        }
    }

    private fun selectStat(stat: Stat) {
        _state.update { state ->
            state.copy(
                selectedStatType = stat,
                legends = filterLegendsByStat(stat, state.selectedStatValue)
            )
        }
    }

    private fun slideStat(value: Int) {
        _state.update { state ->
            state.copy(
                selectedStatValue = value,
                legends = filterLegendsByStat(state.selectedStatType, value)
            )
        }
    }


    private fun selectFilter(filter: FilterOptions) {
        _state.update { state ->
            when (filter) {
                FilterOptions.WEAPONS -> state.copy(
                    selectedFilter = filter,
                    weapons = _weapons,
                    legends = _legends
                )

                FilterOptions.STATS -> state.copy(
                    selectedFilter = filter,
                    legends = filterLegendsByStat(state.selectedStatType, state.selectedStatValue)
                )
            }
        }
    }


    private fun filterLegendsByStat(
        stat: Stat,
        value: Int
    ): List<LegendUi> {
        val legends = _legends.filter { legend ->
            legend.getStat(stat) == value
        }
        return legends
    }

    fun onLegendAction(action: LegendAction) {
        when (action) {
            is LegendAction.SelectLegend -> selectLegend(action.legendId)
            is LegendAction.SearchQuery -> applyFilters(action.text)
            is LegendAction.ToggleSearch -> toggleSearch(action.isOpen)
            is LegendAction.ToggleFilters -> toggleFilters()
            is LegendAction.SelectStat -> selectStat(action.stat)
            is LegendAction.SlideStat -> slideStat(action.value)
            is LegendAction.SelectFilter -> selectFilter(action.filter)
        }
    }

    fun onWeaponAction(action: WeaponAction) {
        when (action) {
            is WeaponAction.Click -> onWeaponSelect(action.weapon)
            is WeaponAction.Select -> onWeaponSelect(action.weapon)
        }
    }
}