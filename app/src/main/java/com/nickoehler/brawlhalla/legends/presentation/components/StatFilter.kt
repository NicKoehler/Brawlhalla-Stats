package com.nickoehler.brawlhalla.legends.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.legends.domain.Stat
import com.nickoehler.brawlhalla.legends.presentation.LegendAction
import com.nickoehler.brawlhalla.legends.presentation.mappers.toColor
import com.nickoehler.brawlhalla.legends.presentation.mappers.toIcon
import com.nickoehler.brawlhalla.legends.presentation.mappers.toLocalizedString
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import com.nickoehler.brawltool.presentation.ui.components.CustomCard

@Composable
fun StatFilter(
    currentStatType: Stat,
    currentStatValue: Int,
    onLegendAction: (LegendAction) -> Unit,
    modifier: Modifier = Modifier
) {
    CustomCard(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Stat.entries.forEach { stat ->
                    Icon(
                        stat.toIcon(),
                        stat.toLocalizedString(),
                        tint = if (stat == currentStatType) {
                            stat.toColor()
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        },
                        modifier = modifier
                            .size(50.dp)
                            .clickable {
                                onLegendAction(LegendAction.SelectStat(stat))
                            }
                    )
                }
            }

            Slider(
                valueRange = 3f..9f,
                value = currentStatValue.toFloat(),
                onValueChange = { onLegendAction(LegendAction.SlideStat(it.toInt())) }
            )
            Text(
                "${currentStatType.toLocalizedString()} $currentStatValue",
                textAlign = TextAlign.Center
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun StatFilterPreview() {
    BrawlhallaTheme {
        Surface {
            StatFilter(
                Stat.STRENGTH,
                3,
                {}
            )
        }
    }
}