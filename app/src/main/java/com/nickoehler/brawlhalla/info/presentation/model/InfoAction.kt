package com.nickoehler.brawlhalla.info.presentation.model

import android.net.Uri

sealed interface InfoAction {
    data class ViewIntent(val uri: Uri) : InfoAction
}
