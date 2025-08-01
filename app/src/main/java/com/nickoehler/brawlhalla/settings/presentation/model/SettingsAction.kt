package com.nickoehler.brawlhalla.settings.presentation.model

import android.net.Uri
import com.nickoehler.brawlhalla.core.domain.model.Theme

sealed interface SettingsAction {
    data class ViewIntent(val uri: Uri) : SettingsAction
    data class SetTheme(val theme: Theme) : SettingsAction
}
