package com.nickoehler.brawlhalla.legends.presentation.mappers

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.legends.domain.Stat
import com.nickoehler.brawlhalla.ui.theme.DefenseColor
import com.nickoehler.brawlhalla.ui.theme.DexterityColor
import com.nickoehler.brawlhalla.ui.theme.SpeedColor
import com.nickoehler.brawlhalla.ui.theme.StrengthColor

@Composable
fun Stat.toIcon(): Painter {
    return painterResource(
        when (this) {
            Stat.STRENGTH -> R.drawable.strength
            Stat.DEFENSE -> R.drawable.defense
            Stat.DEXTERITY -> R.drawable.dexterity
            Stat.SPEED -> R.drawable.speed
        }
    )
}

fun Stat.toColor(): Color {
    return when (this) {
        Stat.STRENGTH -> StrengthColor
        Stat.DEFENSE -> DefenseColor
        Stat.DEXTERITY -> DexterityColor
        Stat.SPEED -> SpeedColor
    }
}

@Composable
fun Stat.toLocalizedString(): String {
    return when (this) {
        Stat.STRENGTH -> stringResource(R.string.strength)
        Stat.DEFENSE -> stringResource(R.string.defense)
        Stat.DEXTERITY -> stringResource(R.string.dexterity)
        Stat.SPEED -> stringResource(R.string.speed)
    }
}

