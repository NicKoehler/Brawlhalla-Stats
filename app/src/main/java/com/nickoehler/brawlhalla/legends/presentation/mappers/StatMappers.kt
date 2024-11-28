package com.nickoehler.brawlhalla.legends.presentation.mappers

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.legends.domain.LegendStat
import com.nickoehler.brawlhalla.ui.theme.DefenseColor
import com.nickoehler.brawlhalla.ui.theme.DexterityColor
import com.nickoehler.brawlhalla.ui.theme.SpeedColor
import com.nickoehler.brawlhalla.ui.theme.StrengthColor

@Composable
fun LegendStat.toIcon(): Painter {
    return painterResource(
        when (this) {
            LegendStat.STRENGTH -> R.drawable.strength
            LegendStat.DEFENSE -> R.drawable.defense
            LegendStat.DEXTERITY -> R.drawable.dexterity
            LegendStat.SPEED -> R.drawable.speed
        }
    )
}

fun LegendStat.toColor(): Color {
    return when (this) {
        LegendStat.STRENGTH -> StrengthColor
        LegendStat.DEFENSE -> DefenseColor
        LegendStat.DEXTERITY -> DexterityColor
        LegendStat.SPEED -> SpeedColor
    }
}

@Composable
fun LegendStat.toLocalizedString(): String {
    return when (this) {
        LegendStat.STRENGTH -> stringResource(R.string.strength)
        LegendStat.DEFENSE -> stringResource(R.string.defense)
        LegendStat.DEXTERITY -> stringResource(R.string.dexterity)
        LegendStat.SPEED -> stringResource(R.string.speed)
    }
}

