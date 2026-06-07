package com.nickoehler.brawlhalla.widgets

import android.content.Context
import android.content.Intent
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.LazyListScope
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.nickoehler.brawlhalla.MainActivity
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.data.database.entities.Guild
import com.nickoehler.brawlhalla.widgets.components.EmptyFavorites
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GuildsWidgetReceiver : GlanceAppWidgetReceiver(), KoinComponent {
    override val glanceAppWidget: GlanceAppWidget = GuildsWidget()

    private val widgetUpdateManager by inject<WidgetUpdateManager>()

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        widgetUpdateManager.syncAll()
    }
}

class GuildsWidget : GlanceAppWidget() {
    override var stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        try {
            provideContent {

                val prefs = currentState<Preferences>()

                val guilds = prefs[
                    stringPreferencesKey("guilds")
                ]?.let {
                    Json.decodeFromString<List<Guild>>(it)
                }.orEmpty()

                GlanceTheme {
                    if (guilds.isEmpty()) {
                        EmptyFavorites(context)
                    } else {
                        LazyColumn(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = GlanceModifier
                                .fillMaxSize()
                                .padding(12.dp)
                                .background(GlanceTheme.colors.widgetBackground)
                        ) {
                            item {
                                Text(
                                    text = context.getString(R.string.guilds),
                                    maxLines = 1,
                                    style = TextStyle(color = GlanceTheme.colors.onBackground)
                                )
                            }
                            guilds(guilds, context)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            provideContent {
                Text(
                    text = e.stackTraceToString().take(1000)
                )
            }
        }
    }

    private fun LazyListScope.guilds(
        guilds: List<Guild>,
        context: Context
    ) {
        items(guilds, { it.id }) { guild ->
            Column {
                Spacer(GlanceModifier.height(8.dp))
                Button(
                    text = guild.name,
                    maxLines = 1,
                    onClick = actionStartActivity(
                        Intent(context, MainActivity::class.java).apply {
                            putExtra("OPEN_GUILD", guild.id)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                    ),
                    modifier = GlanceModifier.fillMaxWidth()
                )
            }
        }
    }
}
