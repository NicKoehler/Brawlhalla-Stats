package com.nickoehler.brawlhalla.core.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.nickoehler.brawlhalla.core.domain.LocalPreferences
import com.nickoehler.brawlhalla.core.domain.model.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.settings by preferencesDataStore(name = "settings")

class Settings(
    private val context: Context
) : LocalPreferences {

    private val themeKey = intPreferencesKey("theme")

    override suspend fun setTheme(theme: Theme) {
        context.settings.edit {
            it[themeKey] = theme.value
        }
    }

    override fun getTheme(): Flow<Theme> {
        return context.settings.data.map { Theme.fromInt(it[themeKey] ?: 0) }
    }
}