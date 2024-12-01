package com.nickoehler.brawlhalla.legends.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickoehler.brawlhalla.core.domain.util.onError
import com.nickoehler.brawlhalla.core.domain.util.onSuccess
import com.nickoehler.brawlhalla.core.presentation.AppBarAction
import com.nickoehler.brawlhalla.core.presentation.CustomAppBarState
import com.nickoehler.brawlhalla.core.presentation.ErrorEvent
import com.nickoehler.brawlhalla.core.presentation.WeaponAction
import com.nickoehler.brawlhalla.core.presentation.models.WeaponUi
import com.nickoehler.brawlhalla.legends.domain.LegendStat
import com.nickoehler.brawlhalla.legends.domain.LegendsDataSource
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
    private var allLegends: List<LegendUi> = emptyList()
    private var allWeapons: List<WeaponUi> = emptyList()
    val state = _state.onStart { if (allLegends.isEmpty()) loadLegends() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            LegendsListState()
        )
    private val _events = Channel<ErrorEvent>()
    val events = _events.receiveAsFlow()

    private fun loadLegends() {
        viewModelScope.launch {
            _state.update { it.copy(isListLoading = true) }

            legendsDataSource.getLegends().onSuccess { legends ->
                allLegends = legends.map { it.toLegendUi() }
                allWeapons = getAllWeapons()

                _state.update { state ->
                    state.copy(
                        isListLoading = false,
                        legends = allLegends,
                        weapons = allWeapons
                    )
                }
            }.onError { error ->
                _state.update { it.copy(isListLoading = false) }
                _events.send(ErrorEvent.Error(error))
            }
        }
    }

    private fun selectLegend(legendId: Int) {
        viewModelScope.launch {
            if (_state.value.selectedLegendUi?.legendId != legendId) {
                _state.update { it.copy(isDetailLoading = true, selectedLegendUi = null) }
                legendsDataSource.getLegendDetail(legendId).onSuccess { legend ->
                    _state.update { state ->
                        state.copy(
                            isDetailLoading = false,
                            selectedLegendUi = legend.toLegendDetailUi()
                        )
                    }
                }.onError { error ->
                    _state.update { it.copy(isDetailLoading = false) }
                    _events.send(ErrorEvent.Error(error))
                }
            }
        }
    }

    private fun searchQuery(query: String) {
        _state.update { state ->
            state.copy(
                appBarState = CustomAppBarState(
                    isOpenSearch = true,
                    searchQuery = query
                ),
                legends = allLegends.filter {
                    filterLegend(query, it)
                },
            )
        }
    }

    private fun filterLegend(query: String, legend: LegendUi): Boolean {
        if (query.isEmpty()) {
            return false
        }
        return legend.legendNameKey.lowercase().contains(query.lowercase())
    }

    private fun toggleSearch(isOpen: Boolean) {
        _state.update { state ->
            state.copy(
                appBarState = CustomAppBarState(
                    isOpenSearch = isOpen,
                    searchQuery = ""
                ),
                openFilters = false,
                weapons = if (isOpen) emptyList() else allWeapons,
                legends = if (isOpen) emptyList() else allLegends
            )
        }
    }

    private fun toggleFilters() {
        _state.update { state ->
            val isOpening = !state.openFilters
            state.copy(
                openFilters = isOpening,
                selectedFilter = if (isOpening) FilterOptions.WEAPONS else state.selectedFilter,
                legends = if (!isOpening) allLegends else state.legends,
                weapons = if (isOpening) allWeapons else state.weapons,
            )
        }
    }

    private fun getAllWeapons(): List<WeaponUi> {
        return allLegends.flatMap { legend ->
            listOf(legend.weaponOne, legend.weaponTwo)
        }.distinct().sortedBy { it.name }
    }


    private fun onWeaponSelect(weapon: WeaponUi, checkWeaponsState: Boolean = true) {
        _state.update { state ->
            val updatedWeaponsState = updateWeaponsState(
                if (checkWeaponsState) state.weapons else allWeapons,
                weapon
            )
            val selected = updatedWeaponsState.filter { it.selected }.map { it.name }
            val filteredLegends = getFilteredLegends(selected)
            val filteredWeapons = getFilteredWeapons(filteredLegends, updatedWeaponsState, selected)

            state.copy(
                openFilters = true,
                appBarState = CustomAppBarState(),
                selectedFilter = FilterOptions.WEAPONS,
                legends = filteredLegends,
                weapons = filteredWeapons
            )
        }
    }

    private fun updateWeaponsState(
        weapons: List<WeaponUi>,
        weapon: WeaponUi
    ) = weapons.map { w ->
        if (w == weapon) {
            w.copy(selected = !w.selected)
        } else {
            w
        }
    }

    private fun getFilteredWeapons(
        filteredLegends: List<LegendUi>,
        updatedWeaponsState: List<WeaponUi>,
        selected: List<String>
    ): List<WeaponUi> {
        return when (selected.size) {
            1 -> allWeapons.filter { weapon ->
                filteredLegends.any { legend ->
                    legend.weaponOne.name == weapon.name
                            || legend.weaponTwo.name == weapon.name
                }
            }.map { weapon ->
                if (selected.contains(weapon.name)) {
                    weapon.copy(
                        selected = true
                    )
                } else {
                    weapon
                }
            }.sortedBy { it.name }.sortedBy { !it.selected }

            2 -> updatedWeaponsState.filter { weapon ->
                filteredLegends.any { legend ->
                    legend.weaponOne.name == weapon.name
                            || legend.weaponTwo.name == weapon.name
                }
            }.sortedBy { it.name }.sortedBy { !it.selected }

            else -> allWeapons
        }
    }

    private fun getFilteredLegends(selectedWeaponsNames: List<String>): List<LegendUi> {
        return when (selectedWeaponsNames.size) {
            1 -> allLegends.filter { legend ->
                selectedWeaponsNames.any { selectedWeapon ->
                    legend.weaponOne.name == selectedWeapon || legend.weaponTwo.name == selectedWeapon
                }
            }

            2 -> allLegends.filter { legend ->
                (legend.weaponOne.name == selectedWeaponsNames[0]
                        && legend.weaponTwo.name == selectedWeaponsNames[1])
                        || (legend.weaponOne.name == selectedWeaponsNames[1]
                        && legend.weaponTwo.name == selectedWeaponsNames[0])
            }

            else -> allLegends
        }

    }


    private fun selectStat(stat: LegendStat, value: Int) {
        _state.update { state ->
            state.copy(
                openFilters = true,
                appBarState = CustomAppBarState(),
                selectedFilter = FilterOptions.STATS,
                selectedStatType = stat,
                selectedStatValue = value,
                legends = filterLegendsByStat(stat, value)
            )
        }
    }


    private fun selectFilter(filter: FilterOptions) {
        _state.update { state ->
            when (filter) {
                FilterOptions.WEAPONS -> state.copy(
                    selectedFilter = filter,
                    weapons = allWeapons,
                    legends = allLegends
                )

                FilterOptions.STATS -> state.copy(
                    selectedFilter = filter,
                    legends = filterLegendsByStat(state.selectedStatType, state.selectedStatValue)
                )
            }
        }
    }


    private fun filterLegendsByStat(
        stat: LegendStat,
        value: Int
    ): List<LegendUi> {
        val legends = allLegends.filter { legend ->
            legend.getStat(stat) == value
        }
        return legends
    }

    fun onLegendAction(action: LegendAction) {
        when (action) {
            is LegendAction.SelectLegend -> selectLegend(action.legendId)
            is LegendAction.ToggleFilters -> toggleFilters()
            is LegendAction.SelectStat -> selectStat(action.stat, action.value)
            is LegendAction.SelectFilter -> selectFilter(action.filter)
        }
    }

    fun onWeaponAction(action: WeaponAction) {
        when (action) {
            is WeaponAction.Click -> onWeaponSelect(action.weapon, false)
            is WeaponAction.Select -> onWeaponSelect(action.weapon)
        }
    }

    fun onAppBarAction(action: AppBarAction) {
        when (action) {
            is AppBarAction.CloseSearch -> toggleSearch(false)
            is AppBarAction.OpenSearch -> toggleSearch(true)
            is AppBarAction.QueryChange -> searchQuery(action.query)
            is AppBarAction.Search -> {}
        }
    }
}