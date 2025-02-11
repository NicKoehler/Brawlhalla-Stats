package com.nickoehler.brawlhalla.ranking.presentation.components.ranking_card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nickoehler.brawlhalla.core.presentation.components.AnimatedLinearProgressBar
import com.nickoehler.brawlhalla.core.presentation.components.shimmerEffect
import com.nickoehler.brawlhalla.core.presentation.models.DisplayableDouble
import com.nickoehler.brawlhalla.ranking.presentation.models.toRankingSoloUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import com.nickoehler.brawlhalla.ui.theme.LoseColor
import com.nickoehler.brawlhalla.ui.theme.WinColor

@Composable
fun RankWinRateRow(winRate: DisplayableDouble? = null) {
    Row(
        modifier = Modifier.padding(end = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (winRate != null) {
            Text(
                "${winRate.formatted}%",
                fontSize = 10.sp,
                lineHeight = 1.sp
            )
            AnimatedLinearProgressBar(
                winRate.value / 100,
                "ratio",
                backgroundColor = LoseColor,
                foregroundColor = WinColor,
                modifier = Modifier
                    .weight(1f)
            )
        } else {
            Box(
                Modifier
                    .size(height = 10.dp, width = 35.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            )
            Box(
                Modifier
                    .height(10.dp)
                    .clip(CircleShape)
                    .weight(1f)
                    .shimmerEffect()
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun RankWinRateRowPreview() {
    BrawlhallaTheme {
        Surface {
            Column {
                RankWinRateRow(rankingSoloSample.toRankingSoloUi().winRate)
                RankWinRateRow()
            }
        }
    }
}