package com.nickoehler.brawlhalla.ranking.presentation.components.ranking_card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.core.presentation.components.shimmerEffect
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toRankingSoloUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toRankingTeamUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun RankName(ranking: RankingUi? = null) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (ranking != null) {
            Text(
                when (ranking) {
                    is RankingUi.RankingSoloUi -> "${ranking.region.flag} · ${ranking.name}"
                    is RankingUi.RankingTeamUi -> "${ranking.region.flag} · ${ranking.teamName}"
                },
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis
            )
        } else {
            Box(
                Modifier
                    .height(10.dp)
                    .padding(end = 10.dp)
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .shimmerEffect()
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun RankNamePreview() {

    BrawlhallaTheme {
        Surface {
            Column {
                RankName(rankingSoloSample.toRankingSoloUi())
                RankName(
                    rankingSoloSample.toRankingSoloUi()
                        .copy(name = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
                )
                RankName(rankingTeamSample.toRankingTeamUi())
                RankName()
            }
        }
    }

}