package com.nickoehler.brawlhalla.ranking.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.nickoehler.brawlhalla.core.presentation.components.shimmerEffect
import com.nickoehler.brawlhalla.ranking.domain.Ranking
import com.nickoehler.brawlhalla.ranking.domain.Region
import com.nickoehler.brawlhalla.ranking.domain.Tier
import com.nickoehler.brawlhalla.ranking.presentation.RankingAction
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toColor
import com.nickoehler.brawlhalla.ranking.presentation.models.toRankingUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import com.nickoehler.brawlhalla.ui.theme.LoseColor
import com.nickoehler.brawlhalla.ui.theme.WinColor

@Composable
fun RankingCard(
    modifier: Modifier = Modifier,
    ranking: RankingUi? = null,
    onRankingAction: (RankingAction) -> Unit = {}
) {
    CustomCard(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        contentPadding = 10.dp,
        onClick = {
            if (ranking != null) {
                onRankingAction(
                    RankingAction.SelectRanking(ranking.brawlhallaId)
                )
            }
        }
    ) {
        Box(
            Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceBright),
            contentAlignment = Alignment.Center
        ) {
            if (ranking != null) {
                Text(
                    ranking.rank.formatted,
                    fontSize = 10.sp,
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
        Spacer(Modifier.size(10.dp))


        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(8.dp)

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 10.dp),
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (ranking != null) {
                            Text(
                                "${ranking.region.flag} · ${ranking.name}",
                                fontWeight = FontWeight.Bold,
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
                if (ranking != null) {
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
                } else {
                    Box(
                        Modifier
                            .size(height = 20.dp, width = 60.dp)
                            .clip(CircleShape)
                            .shimmerEffect()
                    )
                }

            }

            Row(
                modifier = Modifier.padding(end = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (ranking != null) {
                    Text(
                        "${ranking.winRate.formatted}%",
                        fontSize = 10.sp,
                        lineHeight = 1.sp
                    )
                    AnimatedLinearProgressBar(
                        ranking.winRate.value / 100,
                        "ratio",
                        backgroundColor = LoseColor,
                        foregroundColor = WinColor,
                        modifier = Modifier
                            .weight(1f)
                    )
                } else {
                    Box(
                        Modifier
                            .size(height = 10.dp, width = 30.dp)
                            .clip(CircleShape)
                            .shimmerEffect()
                    )
                    Box(
                        Modifier
                            .size(height = 10.dp, width = 80.dp)
                            .clip(CircleShape)
                            .weight(1f)
                            .shimmerEffect()
                    )
                }
            }
        }
    }
}


@PreviewLightDark
@Composable
private fun RankingCardPreview() {
    BrawlhallaTheme {
        Surface {
            Column {
                RankingCard(
                    modifier = Modifier.fillMaxWidth(),
                    ranking = rankingSample.toRankingUi()
                )

                RankingCard(
                    modifier = Modifier.fillMaxWidth(),
                )
            }
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