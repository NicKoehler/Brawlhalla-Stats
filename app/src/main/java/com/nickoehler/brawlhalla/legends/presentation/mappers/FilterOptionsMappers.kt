package com.nickoehler.brawlhalla.legends.presentation.mappers

import android.content.Context
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.legends.presentation.models.FilterOptions

fun FilterOptions.toLocalizedString(context: Context): String {
    return context.getString(
        when (this) {
            FilterOptions.WEAPONS -> R.string.weapons
            FilterOptions.STATS -> R.string.stats
        }
    )
}