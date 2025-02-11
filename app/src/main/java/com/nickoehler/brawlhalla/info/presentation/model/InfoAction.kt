package com.nickoehler.brawlhalla.info.presentation.model

import android.content.Context

sealed interface InfoAction {
    data class GithubPressed(val context: Context) : InfoAction
}
