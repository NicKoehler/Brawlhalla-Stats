package com.nickoehler.brawlhalla.settings.presentation.model

import com.nickoehler.brawlhalla.core.domain.model.Theme

data class SettingsState(
    val currentTheme: Theme = Theme.System,
    val currentLanguage: Language = Language("", ""),
    val currentSetting: SettingType? = null
)
