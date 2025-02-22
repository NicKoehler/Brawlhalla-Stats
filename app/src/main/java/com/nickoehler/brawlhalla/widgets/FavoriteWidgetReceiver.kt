package com.nickoehler.brawlhalla.widgets

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.actionStartActivity
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.Text
import com.nickoehler.brawlhalla.MainActivity
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.data.database.entities.Clan
import com.nickoehler.brawlhalla.core.data.database.entities.Player
import kotlinx.serialization.json.Json

class FavoriteWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = FavoriteWidget()
}

class FavoriteWidget : GlanceAppWidget() {
    override var stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {

            val prefs = currentState<Preferences>()

            val players = prefs[
                stringPreferencesKey("players")
            ]?.let {
                Json.decodeFromString<List<Player>>(it)
            }.orEmpty()

            val clans = prefs[
                stringPreferencesKey("clans")
            ]?.let {
                Json.decodeFromString<List<Clan>>(it)
            }.orEmpty()

            GlanceTheme {
                if (players.isEmpty() && clans.isEmpty()) {
                    Box(
                        modifier = GlanceModifier
                            .fillMaxSize()
                            .padding(12.dp)
                            .background(GlanceTheme.colors.background),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            text = context.getString(R.string.favorites),
                            onClick = actionStartActivity<MainActivity>(),
                        )
                    }
                } else {
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = GlanceModifier
                            .fillMaxSize()
                            .padding(12.dp)
                            .background(GlanceTheme.colors.background)
                    ) {
                        if (players.isNotEmpty()) {
                            item {
                                Text(context.getString(R.string.players))
                            }
                            item {
                                Players(players, context)
                            }
                        }

                        if (clans.isNotEmpty()) {
                            item {
                                Text(context.getString(R.string.clans))
                            }
                            item {
                                Clans(clans, context)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun Players(players: List<Player>, context: Context) {
        Column(
            modifier = GlanceModifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            players.forEach { player ->
                Button(
                    text = player.name,
                    onClick = actionStartActivity(
                        Intent(context, MainActivity::class.java).apply {
                            putExtra("OPEN_STAT", player.id)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                    ),
                    modifier = GlanceModifier.fillMaxWidth()
                )
                Spacer(GlanceModifier.height(4.dp))
            }
        }
    }

    @Composable
    fun Clans(clans: List<Clan>, context: Context) {
        Column(
            modifier = GlanceModifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            clans.forEach { clan ->
                Button(
                    text = clan.name,
                    onClick = actionStartActivity(
                        Intent(context, MainActivity::class.java).apply {
                            putExtra("OPEN_CLAN", clan.id)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                    ),
                    modifier = GlanceModifier.fillMaxWidth()
                )
                Spacer(GlanceModifier.height(4.dp))
            }
        }
    }
}

