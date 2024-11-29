package com.nickoehler.brawlhalla.legends.presentation.mappers

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.legends.presentation.models.FilterOptions

@Composable
fun FilterOptions.toLocalizedString(): String {
    return stringResource(
        when (this) {
            FilterOptions.WEAPONS -> R.string.weapons
            FilterOptions.STATS -> R.string.stats
        }
    )
}