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
import com.nickoehler.brawlhalla.core.data.database.entities.Clan
import com.nickoehler.brawlhalla.widgets.components.EmptyFavorites
import kotlinx.serialization.json.Json

class ClansWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = ClansWidget()
}

class ClansWidget : GlanceAppWidget() {
    override var stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {

            val prefs = currentState<Preferences>()

            val clans = prefs[
                stringPreferencesKey("clans")
            ]?.let {
                Json.decodeFromString<List<Clan>>(it)
            }.orEmpty()

            GlanceTheme {
                if (clans.isEmpty()) {
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
                                text = context.getString(R.string.clans),
                                maxLines = 1,
                                style = TextStyle(color = GlanceTheme.colors.onBackground)
                            )
                        }
                        clans(clans, context)
                    }
                }
            }
        }
    }

    private fun LazyListScope.clans(
        clans: List<Clan>,
        context: Context
    ) {
        items(clans, { it.id.toLong() }) { clan ->
            Column {
                Spacer(GlanceModifier.height(8.dp))
                Button(
                    text = clan.name,
                    maxLines = 1,
                    onClick = actionStartActivity(
                        Intent(context, MainActivity::class.java).apply {
                            putExtra("OPEN_CLAN", clan.id)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                    ),
                    modifier = GlanceModifier.fillMaxWidth()
                )
            }
        }
    }
}

