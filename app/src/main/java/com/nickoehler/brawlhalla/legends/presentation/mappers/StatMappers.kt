package com.nickoehler.brawlhalla.legends.presentation.mappers

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
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

fun LegendStat.toLocalizedString(context: Context): String {
    return when (this) {
        LegendStat.STRENGTH -> context.getString(R.string.strength)
        LegendStat.DEFENSE -> context.getString(R.string.defense)
        LegendStat.DEXTERITY -> context.getString(R.string.dexterity)
        LegendStat.SPEED -> context.getString(R.string.speed)
    }
}

