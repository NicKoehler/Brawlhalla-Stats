package com.nickoehler.brawlhalla.settings.presentation

import AppLocaleManager
import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickoehler.brawlhalla.core.data.datastore.Settings
import com.nickoehler.brawlhalla.core.domain.model.Theme
import com.nickoehler.brawlhalla.settings.presentation.model.Language
import com.nickoehler.brawlhalla.settings.presentation.model.SettingType
import com.nickoehler.brawlhalla.settings.presentation.model.SettingsAction
import com.nickoehler.brawlhalla.settings.presentation.model.SettingsEvent
import com.nickoehler.brawlhalla.settings.presentation.model.SettingsState
import com.nickoehler.brawlhalla.settings.presentation.model.appLanguages
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class SettingsViewModel(
    val settings: Settings,
    val localeManager: AppLocaleManager,
    val context: Context,
) : ViewModel() {

    private val _events = Channel<SettingsEvent>()
    val events = _events.receiveAsFlow()


    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow().onStart {
        val currentLanguage =
            appLanguages.find { it.code == localeManager.getLanguageCode(context) }
                ?: Language("", "")
        collectSettings(currentLanguage)
    }.stateIn(
        scope = viewModelScope,
        initialValue = SettingsState(),
        started = SharingStarted.WhileSubscribed(5_000)
    )

    private fun collectSettings(currentLanguage: Language) {
        viewModelScope.launch {
            settings.getTheme().collect { theme ->
                _state.update { it.copy(currentTheme = theme, currentLanguage = currentLanguage) }
            }
        }
    }

    fun onSettingsAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.ViewIntent -> sendEvent(action.uri)
            is SettingsAction.SetTheme -> setTheme(action.theme)
            is SettingsAction.SetLanguage -> setLanguage(action.language)
            is SettingsAction.SelectSetting -> selectSetting(action.settingType)
        }
    }

    private fun selectSetting(settingType: SettingType?) {
        _state.update {
            it.copy(currentSetting = settingType)
        }
    }

    fun setLanguage(language: Language) {
        localeManager.changeLanguage(context, language.code)
        _state.update { state ->
            state.copy(currentLanguage = language)
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