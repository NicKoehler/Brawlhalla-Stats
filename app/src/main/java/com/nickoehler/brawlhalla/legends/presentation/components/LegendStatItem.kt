package com.nickoehler.brawlhalla.legends.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nickoehler.brawlhalla.core.presentation.components.AnimatedLinearProgressBar
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.legends.domain.LegendStat
import com.nickoehler.brawlhalla.legends.presentation.LegendAction
import com.nickoehler.brawlhalla.legends.presentation.mappers.toColor
import com.nickoehler.brawlhalla.legends.presentation.mappers.toIcon
import com.nickoehler.brawlhalla.legends.presentation.mappers.toLocalizedString
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme


@Composable
fun LegendStatItem(
    stat: LegendStat,
    statValue: Int,
    onLegendAction: (LegendAction) -> Unit,
    delayMillis: Int = 0,
    modifier: Modifier = Modifier
) {
    CustomCard(
        modifier = modifier,
        onClick = {
            onLegendAction(LegendAction.SelectStat(stat, statValue))
        }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Icon(
                stat.toIcon(),
                stat.toLocalizedString(),
                tint = stat.toColor(),
                modifier = modifier.size(40.dp)
            )
            AnimatedLinearProgressBar(
                statValue.toFloat() / 10f,
                stat.toString(),
                height = 40.dp,
                delayMillis = delayMillis
            )
            Text(
                statValue.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun StatRowPreview() {
    BrawlhallaTheme {
        Surface {
            LegendStatItem(
                LegendStat.STRENGTH,
                10,
                {}
            )
        }
    }
}