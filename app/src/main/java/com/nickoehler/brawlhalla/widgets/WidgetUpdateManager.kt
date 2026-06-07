package com.nickoehler.brawlhalla.widgets

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import com.nickoehler.brawlhalla.core.data.database.AppDatabase
import com.nickoehler.brawlhalla.core.data.database.entities.Guild
import com.nickoehler.brawlhalla.core.data.database.entities.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class WidgetUpdateManager(
    private val context: Context,
    private val database: AppDatabase
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val playerDao = database.playerDao()
    private val guildDao = database.guildDao()

    fun syncAll() {
        scope.launch {
            updatePlayers(playerDao.getAllPlayers().first())
            updateGuilds(guildDao.getAllGuilds().first())
        }
    }

    suspend fun updatePlayers(players: List<Player>) {
        val glanceIds = GlanceAppWidgetManager(context).getGlanceIds(PlayersWidget::class.java)
        glanceIds.forEach { glanceId ->
            updateAppWidgetState(context, glanceId) { prefs ->
                prefs[stringPreferencesKey("players")] = Json.encodeToString(players)
            }
            PlayersWidget().update(context, glanceId)
        }
    }

    suspend fun updateGuilds(guilds: List<Guild>) {
        val glanceIds = GlanceAppWidgetManager(context).getGlanceIds(GuildsWidget::class.java)
        glanceIds.forEach { glanceId ->
            updateAppWidgetState(context, glanceId) { prefs ->
                prefs[stringPreferencesKey("guilds")] = Json.encodeToString(guilds)
            }
            GuildsWidget().update(context, glanceId)
        }
    }
}
