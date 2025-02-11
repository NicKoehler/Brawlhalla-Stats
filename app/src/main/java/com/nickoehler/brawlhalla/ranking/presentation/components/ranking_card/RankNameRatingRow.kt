package com.nickoehler.brawlhalla.ranking.presentation.components.ranking_card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.core.presentation.components.shimmerEffect
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toRankingSoloUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun RankNameRatingRow(ranking: RankingUi? = null) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(end = 10.dp),
    ) {
        Column(modifier = Modifier.weight(1f)) {
            RankName(ranking)
        }

        if (ranking != null) {
            TierBox(
                when (ranking) {
                    is RankingUi.RankingSoloUi -> ranking.rating
                    is RankingUi.RankingTeamUi -> ranking.rating
                }, when (ranking) {
                    is RankingUi.RankingSoloUi -> ranking.tier
                    is RankingUi.RankingTeamUi -> ranking.tier
                }
            )
        } else {
            Box(
                Modifier
                    .size(height = 20.dp, width = 60.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun RankNameRatingRowPreview() {
    BrawlhallaTheme {
        Surface {
            Column {
                RankNameRatingRow(ranking = rankingSoloSample.toRankingSoloUi())
                RankNameRatingRow()
            }
        }
    }

}