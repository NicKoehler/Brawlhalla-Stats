package com.nickoehler.brawlhalla.core.domain

import com.nickoehler.brawlhalla.core.domain.model.Theme
import kotlinx.coroutines.flow.Flow


interface LocalPreferences {
    suspend fun setTheme(theme: Theme)
    fun getTheme(): Flow<Theme>
}