package com.nickoehler.brawlhalla.core.presentation;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickoehler.brawlhalla.core.data.datastore.Settings;
import com.nickoehler.brawlhalla.core.domain.model.Theme
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ThemeViewModel(
    settings: Settings
) : ViewModel() {
    val theme: StateFlow<Theme> =
        settings.getTheme().map { it }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Theme.System
        )

}
