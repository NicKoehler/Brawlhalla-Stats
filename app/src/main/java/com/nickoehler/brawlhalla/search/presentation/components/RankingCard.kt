package com.nickoehler.brawlhalla.search.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        horizontalArrangement = Arrangement.SpaceBetween,
        contentPadding = 0.dp
    ) {
        Box(
            Modifier
                .size(50.dp)
                .padding(10.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceBright),
            contentAlignment = Alignment.Center
        ) {
            Text(
                ranking.rank.formatted,
                fontSize = 10.sp
            )
        }
        Text(
            ranking.region.name.name,
            fontSize = 10.sp
        )
        Spacer(Modifier.size(10.dp))
        Text(
            ranking.name,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        Spacer(Modifier.size(10.dp))

        Box(
            Modifier
                .size(50.dp)
                .weight(0.4f)
                .padding(10.dp)
                .clip(CircleShape)
                .background(ranking.tier.toColor()),
            contentAlignment = Alignment.Center
        ) {
            Text(
                ranking.rating.formatted,
                color = Color.Black.copy(alpha = 0.5f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }
    }
}


@PreviewLightDark
@Composable
private fun RankingCardPreview() {
    BrawlhallaTheme {
        Surface {
            RankingCard(
                modifier = Modifier.fillMaxWidth(),
                ranking = rankingSample.toRankingUi()
            )
        }
    }
}


internal val rankingSample = Ranking(
    rank = 1,
    name = "Kororonâ\u0098\u0086",
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