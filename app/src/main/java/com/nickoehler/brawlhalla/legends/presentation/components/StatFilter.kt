package com.nickoehler.brawlhalla.legends.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.legends.domain.LegendStat
import com.nickoehler.brawlhalla.legends.presentation.LegendAction
import com.nickoehler.brawlhalla.legends.presentation.mappers.toColor
import com.nickoehler.brawlhalla.legends.presentation.mappers.toIcon
import com.nickoehler.brawlhalla.legends.presentation.mappers.toLocalizedString
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun StatFilter(
    currentStatType: LegendStat,
    currentStatValue: Int,
    onLegendAction: (LegendAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    CustomCard(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LegendStat.entries.forEach { stat ->
                    Icon(
                        stat.toIcon(),
                        stat.toLocalizedString(context),
                        tint = if (stat == currentStatType) {
                            stat.toColor()
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        },
                        modifier = modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(38f))
                            .clickable(onClickLabel = stat.toLocalizedString(context)) {
                                onLegendAction(LegendAction.SelectStat(stat, currentStatValue))
                            }
                    )
                }
            }

            Slider(
                valueRange = 3f..9f,
                value = currentStatValue.toFloat(),
                onValueChange = {
                    onLegendAction(
                        LegendAction.SelectStat(
                            currentStatType,
                            it.toInt()
                        )
                    )
                },
                steps = 5
            )
            Text(
                "${currentStatType.toLocalizedString(context)} $currentStatValue",
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
                LegendStat.STRENGTH,
                3,
                {}
            )
        }
    }
}