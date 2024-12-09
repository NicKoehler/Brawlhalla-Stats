package com.nickoehler.brawlhalla.legends.presentation.models

import android.content.Context
import com.nickoehler.brawlhalla.R

enum class FilterOptions {
    WEAPONS,
    STATS
}

fun FilterOptions.toLocalizedString(context: Context): String {
    return context.getString(
        when (this) {
            FilterOptions.WEAPONS -> R.string.weapons
            FilterOptions.STATS -> R.string.stats
        }
    )
}