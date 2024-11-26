package com.nickoehler.brawlhalla.legends.presentation.mappers

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.legends.presentation.models.FilterOptions

@Composable
fun FilterOptions.toLocalizedString(): String {
    return when (this) {
        FilterOptions.WEAPONS -> stringResource(R.string.weapons)
        FilterOptions.STATS -> stringResource(R.string.stats)
    }
}