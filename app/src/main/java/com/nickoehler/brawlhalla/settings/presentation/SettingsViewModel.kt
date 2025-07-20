package com.nickoehler.brawlhalla.settings.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickoehler.brawlhalla.core.data.datastore.Settings
import com.nickoehler.brawlhalla.core.domain.model.Theme
import com.nickoehler.brawlhalla.settings.presentation.model.SettingsAction
import com.nickoehler.brawlhalla.settings.presentation.model.SettingsEvent
import com.nickoehler.brawlhalla.settings.presentation.model.SettingsState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(val settings: Settings) : ViewModel() {

    private val _events = Channel<SettingsEvent>()
    val events = _events.receiveAsFlow()


    private val _state = MutableStateFlow(SettingsState());
    val state = _state.asStateFlow().onStart {
        collectSettings()
    }.stateIn(
        scope = viewModelScope,
        initialValue = SettingsState(),
        started = SharingStarted.WhileSubscribed(5_000)
    )

    private fun collectSettings() {
        viewModelScope.launch {
            settings.getTheme().collect { theme ->
                _state.update { it.copy(currentTheme = theme) }
            }
        }
    }

    fun onSettingsAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.ViewIntent -> sendEvent(action.uri)
            is SettingsAction.SetTheme -> setTheme(action.theme)
        }
    }

    private fun setTheme(theme: Theme) {
        viewModelScope.launch {
            settings.setTheme(theme)
        }
    }

    private fun sendEvent(uri: Uri) {
        viewModelScope.launch {
            _events.send(
                SettingsEvent.ViewIntent(
                    uri
                )
            )
        }
    }
}