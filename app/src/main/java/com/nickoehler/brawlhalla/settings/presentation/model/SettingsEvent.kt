package com.nickoehler.brawlhalla.settings.presentation.model

import android.net.Uri

sealed interface SettingsEvent {
    data class ViewIntent(val uri: Uri) : SettingsEvent
}
