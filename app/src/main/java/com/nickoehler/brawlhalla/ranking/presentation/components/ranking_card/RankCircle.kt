package com.nickoehler.brawlhalla.ranking.presentation.components.ranking_card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nickoehler.brawlhalla.core.presentation.components.shimmerEffect
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toRankingSoloUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun RankCircle(ranking: RankingUi?) {
    Box(
        Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceBright),
        contentAlignment = Alignment.Center
    ) {
        if (ranking != null) {
            Text(
                when (ranking) {
                    is RankingUi.RankingSoloUi -> ranking.rank.formatted
                    is RankingUi.RankingTeamUi -> ranking.rank.formatted
                },
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        } else {
            Box(
                Modifier
                    .fillMaxSize()
                    .shimmerEffect()
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun RankCirclePreview() {
    BrawlhallaTheme {
        Surface {
            Column {
                RankCircle(rankingSoloSample.toRankingSoloUi())
            }
        }
    }

}