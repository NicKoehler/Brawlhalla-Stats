package com.nickoehler.brawlhalla.ranking.presentation.components.ranking_card

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.ranking.domain.Player
import com.nickoehler.brawlhalla.ranking.domain.Ranking
import com.nickoehler.brawlhalla.ranking.domain.Region
import com.nickoehler.brawlhalla.ranking.domain.Tier
import com.nickoehler.brawlhalla.ranking.presentation.RankingAction
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toRankingUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import kotlinx.coroutines.delay

@Composable
fun RankingCard(
    modifier: Modifier = Modifier,
    ranking: RankingUi? = null,
    onRankingAction: (RankingAction) -> Unit = {}
) {

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(100L)
        visible = true
    }

    var expanded by remember { mutableStateOf(false) }

    CustomCard(
        modifier = modifier.animateContentSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
        contentPadding = PaddingValues(10.dp),
        onClick = {
            if (ranking != null) {
                when (ranking.players.size) {
                    1 -> onRankingAction(
                        RankingAction.SelectRanking(
                            ranking.players.first().id
                        )
                    )
                    else -> expanded = !expanded
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RankCircle(ranking)

                Spacer(Modifier.size(10.dp))

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RankNameRatingRow(ranking)
                    RankWinRateRow(ranking?.winRate)
                }
            }

            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column {
                    Spacer(Modifier.size(12.dp))

                    ranking?.players?.forEach { player ->
                        FilledTonalButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                onRankingAction(
                                    RankingAction.SelectRanking(player.id)
                                )
                            }
                        ) {
                            Text(player.username)
                        }

                        Spacer(Modifier.size(8.dp))
                    }
                }
            }
        }
    }
}

internal val rankingSoloSample = Ranking(
    rank = 1,
    players = listOf(
        Player(
            id = 1,
            username = "Kororonâ\u0098\u0086",
        )
    ),
    rating = 2000,
    tier = Tier.VALHALLAN,
    losses = 2345,
    wins = 33424,
    region = Region.EU,
    bestRating = 2000
)

@PreviewLightDark
@Composable
private fun RankingCardSinglePreview() {
    BrawlhallaTheme {
        Surface {
            Column {
                RankingCard(
                    modifier = Modifier.fillMaxWidth(),
                    ranking = rankingSoloSample.toRankingUi(),
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun RankingCardDuoPreview() {
    BrawlhallaTheme {
        Surface {
            Column {
                RankingCard(
                    modifier = Modifier.fillMaxWidth(),
                    ranking = rankingSoloSample.copy(
                        players = listOf(
                            Player(
                                id = 1,
                                username = "Kororonâ\u0098\u0086",
                            ),
                            Player(
                                id = 2,
                                username = "test",
                            )
                        ),
                    ).toRankingUi(),
                )
            }
        }
    }
}



