package com.nickoehler.brawlhalla.info.presentation.model

import android.net.Uri

sealed interface InfoEvent {
    data class ViewIntent(val uri: Uri) : InfoEvent
}
