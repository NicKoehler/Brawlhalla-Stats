package com.nickoehler.brawlhalla.ranking.presentation.components.ranking_card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.ranking.domain.Ranking
import com.nickoehler.brawlhalla.ranking.domain.Region
import com.nickoehler.brawlhalla.ranking.domain.Tier
import com.nickoehler.brawlhalla.ranking.presentation.RankingAction
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toRankingSoloUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun RankingSoloCard(
    ranking: RankingUi.RankingSoloUi? = null,
    onRankingAction: (RankingAction) -> Unit = {},
    modifier: Modifier = Modifier
) {
    CustomCard(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        contentPadding = 10.dp,
        onClick = {
            if (ranking != null) {
                onRankingAction(
                    RankingAction.SelectRanking(
                        ranking.brawlhallaId
                    )
                )
            }
        }
    ) {
        RankCircle(ranking)
        Spacer(Modifier.size(10.dp))
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(8.dp)

        ) {
            RankNameRatingRow(ranking)
            RankWinRateRow(ranking)
        }
    }
}

@PreviewLightDark
@Composable
private fun RankingSoloCardPreview() {
    BrawlhallaTheme {
        Surface {
            Column {
                RankingSoloCard(
                    rankingSoloSample.toRankingSoloUi()
                )
                RankingSoloCard()
            }
        }
    }
}


internal val rankingSoloSample = Ranking.RankingSolo(
    rank = 1,
    name = "Kororonâ\u0098\u0086",
    brawlhallaId = 1,
    bestLegend = 3,
    bestLegendGames = 3,
    bestLegendWins = 3,
    rating = 2000,
    tier = Tier.VALHALLAN,
    games = 53841,
    wins = 33424,
    region = Region.EU,
    peakRating = 2000
)