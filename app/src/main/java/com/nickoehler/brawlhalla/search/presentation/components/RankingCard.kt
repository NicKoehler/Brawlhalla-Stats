package com.nickoehler.brawlhalla.search.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.search.domain.Ranking
import com.nickoehler.brawlhalla.search.domain.Region
import com.nickoehler.brawlhalla.search.domain.Tier
import com.nickoehler.brawlhalla.search.presentation.models.RankingUi
import com.nickoehler.brawlhalla.search.presentation.models.toColor
import com.nickoehler.brawlhalla.search.presentation.models.toRankingUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun RankingCard(
    modifier: Modifier = Modifier,
    ranking: RankingUi
) {
    CustomCard(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(ranking.rank.toString())
        Text(ranking.name)

        Box(
            Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(ranking.tier.toColor())
        )
    }
}


@PreviewLightDark
@Composable
private fun RankingCardPreview() {
    BrawlhallaTheme {
        Surface {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                RankingCard(
                    modifier = Modifier.fillMaxWidth(),
                    ranking = rankingSample.toRankingUi()
                )
            }
        }
    }
}


internal val rankingSample = Ranking(
    rank = 1,
    name = "Nic",
    brawlhallaId = 1,
    bestLegend = 3,
    bestLegendGames = 3,
    bestLegendWins = 3,
    rating = 2000,
    tier = Tier.DIAMOND,
    games = 23849,
    wins = 23445,
    region = Region.EU,
    peakRating = 2000
)