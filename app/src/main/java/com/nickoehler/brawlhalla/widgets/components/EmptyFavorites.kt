package com.nickoehler.brawlhalla.widgets.components

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import com.nickoehler.brawlhalla.MainActivity
import com.nickoehler.brawlhalla.R

@Composable
fun EmptyFavorites(context: Context) {
    Box(
        modifier = GlanceModifier
            .fillMaxSize()
            .padding(12.dp)
            .background(GlanceTheme.colors.widgetBackground),
        contentAlignment = Alignment.Center
    ) {
        Button(
            text = context.getString(R.string.favorites),
            onClick = actionStartActivity(
                Intent(context, MainActivity::class.java).apply {
                    flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            ),
        )
    }
}