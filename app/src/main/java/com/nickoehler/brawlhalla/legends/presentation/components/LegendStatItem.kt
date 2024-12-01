package com.nickoehler.brawlhalla.legends.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nickoehler.brawlhalla.core.presentation.components.AnimatedLinearProgressBar
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.core.presentation.components.shimmerEffect
import com.nickoehler.brawlhalla.legends.domain.LegendStat
import com.nickoehler.brawlhalla.legends.presentation.LegendAction
import com.nickoehler.brawlhalla.legends.presentation.mappers.toColor
import com.nickoehler.brawlhalla.legends.presentation.mappers.toIcon
import com.nickoehler.brawlhalla.legends.presentation.mappers.toLocalizedString
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme


@Composable
fun LegendStatItem(
    stat: LegendStat,
    statValue: Int? = null,
    onLegendAction: (LegendAction) -> Unit = {},
    delayMillis: Int = 0,
    height: Dp = 40.dp,
    modifier: Modifier = Modifier
) {
    CustomCard(
        modifier = modifier.fillMaxWidth(),
        contentPadding = 15.dp,
        onClick = {
            if (statValue != null) onLegendAction(LegendAction.SelectStat(stat, statValue))
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Spacer(Modifier.size(5.dp))
        Icon(
            stat.toIcon(),
            stat.toLocalizedString(),
            tint = stat.toColor(),
            modifier = modifier.size(height)
        )
        if (statValue != null) {
            AnimatedLinearProgressBar(
                statValue.toFloat() / 10f,
                stat.toString(),
                height = height,
                delayMillis = delayMillis,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .weight(1f)
            )
            Text(
                statValue.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        } else {
            Box(
                Modifier
                    .height(40.dp)
                    .weight(1f)
                    .padding(horizontal = 20.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            )
            Box(
                Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            )
        }
        Spacer(Modifier.size(5.dp))
    }

}

@PreviewLightDark
@Composable
private fun StatRowPreview() {
    BrawlhallaTheme {
        Surface {
            Column {

                LegendStatItem(
                    LegendStat.STRENGTH,
                    10,
                    {}
                )
                LegendStatItem(LegendStat.STRENGTH)
            }
        }
    }
}