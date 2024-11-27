package com.nickoehler.brawlhalla.legends.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
fun StatRow(
    stat: Stat,
    statValue: Int,
    onLegendAction: (LegendAction) -> Unit,
    modifier: Modifier = Modifier
) {
    CustomCard(
        modifier = modifier,
        onClick = {
            onLegendAction(LegendAction.SelectStat(stat, statValue))
        }
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CustomCard(
                modifier = Modifier.size(60.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                contentPadding = 0.dp,
                color = MaterialTheme.colorScheme.surfaceBright
            ) {
                Text(
                    statValue.toString(),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                repeat(statValue) {
                    StatDot(MaterialTheme.colorScheme.primary)
                }
                repeat(10 - statValue) {
                    StatDot(MaterialTheme.colorScheme.surfaceBright)
                }
            }
            Icon(
                stat.toIcon(),
                stat.toLocalizedString(),
                tint = stat.toColor(),
                modifier = modifier.size(50.dp)
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun StatRowPreview() {
    BrawlhallaTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            StatRow(
                Stat.STRENGTH,
                7,
                {}
            )
        }
    }
}