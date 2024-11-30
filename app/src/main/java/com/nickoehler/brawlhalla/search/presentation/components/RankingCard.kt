package com.nickoehler.brawlhalla.search.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.nickoehler.brawlhalla.core.presentation.components.AnimatedLinearProgressBar
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.search.domain.Ranking
import com.nickoehler.brawlhalla.search.domain.Region
import com.nickoehler.brawlhalla.search.domain.Tier
import com.nickoehler.brawlhalla.search.presentation.models.RankingUi
import com.nickoehler.brawlhalla.search.presentation.models.toColor
import com.nickoehler.brawlhalla.search.presentation.models.toRankingUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import com.nickoehler.brawlhalla.ui.theme.LoseColor
import com.nickoehler.brawlhalla.ui.theme.WinColor

@Composable
fun RankingCard(
    modifier: Modifier = Modifier,
    ranking: RankingUi
) {
    CustomCard(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        contentPadding = 10.dp
    ) {
        Box(
            Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceBright),
            contentAlignment = Alignment.Center
        ) {
            Text(
                ranking.rank.formatted,
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            )
        }
        Spacer(Modifier.size(10.dp))


        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(8.dp)

        ) {
            Row {

                Column(modifier = Modifier.weight(1f)) {

                    Text(
                        ranking.name,
                        fontWeight = FontWeight.Bold,
                    )

                }
                Box(
                    Modifier
                        .clip(CircleShape)
                        .background(ranking.tier.toColor())
                        .padding(horizontal = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        ranking.rating.formatted,
                        color = Color.Black.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    ranking.region.name.name,
                    fontSize = 10.sp
                )
                AnimatedLinearProgressBar(
                    ranking.wins.value.toFloat() / ranking.games.value.toFloat(),
                    "ratio",
                    backgroundColor = LoseColor,
                    foregroundColor = WinColor,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Spacer(Modifier.size(10.dp))
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
    tier = Tier.VALHALLAN,
    games = 53841,
    wins = 33424,
    region = Region.EU,
    peakRating = 2000
)