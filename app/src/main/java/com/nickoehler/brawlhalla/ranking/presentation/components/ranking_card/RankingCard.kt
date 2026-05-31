package com.nickoehler.brawlhalla.ranking.presentation.components.ranking_card

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
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
import com.nickoehler.brawlhalla.ranking.domain.GameMode
import com.nickoehler.brawlhalla.ranking.domain.Player
import com.nickoehler.brawlhalla.ranking.domain.Ranking
import com.nickoehler.brawlhalla.ranking.domain.Region
import com.nickoehler.brawlhalla.ranking.domain.Tier
import com.nickoehler.brawlhalla.ranking.presentation.RankingAction
import com.nickoehler.brawlhalla.ranking.presentation.models.BracketUi
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toBracketUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toRankingUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import kotlinx.coroutines.delay

@Composable
fun RankingCard(
    modifier: Modifier = Modifier,
    ranking: RankingUi? = null,
    selectedGameMode: BracketUi = GameMode.ONE_VS_ONE.toBracketUi(),
    onRankingAction: (RankingAction) -> Unit = {}
) {

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(100L)
        visible = true
    }

    val animatedFloat by animateFloatAsState(if (visible) 1f else 0.9f)

    CustomCard(
        modifier = modifier,
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

                    else -> {}
                }
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
            RankWinRateRow(
                ranking?.winRate
            )
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
private fun RankingCardPreview() {
    BrawlhallaTheme {
        Surface {
            Column {
                RankingCard(
                    modifier = Modifier.fillMaxWidth(),
                    ranking = rankingSoloSample.toRankingUi(),
                    selectedGameMode = GameMode.ONE_VS_ONE.toBracketUi()
                )
            }
        }
    }
}

