package com.nickoehler.brawlhalla.ranking.presentation.components.ranking_card

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.ranking.data.mappers.toRegion
import com.nickoehler.brawlhalla.ranking.data.mappers.toTier
import com.nickoehler.brawlhalla.ranking.domain.Ranking
import com.nickoehler.brawlhalla.ranking.presentation.RankingAction
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toRankingTeamUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun RankingTeamCard(
    ranking: RankingUi.RankingTeamUi? = null,
    onRankingAction: (RankingAction) -> Unit = {},
    defaultExpanded: Boolean = false,
    modifier: Modifier = Modifier
) {
    var expanded by remember {
        mutableStateOf(defaultExpanded)
    }
    CustomCard(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        contentPadding = 10.dp,
        onClick = { expanded = !expanded }
    ) {
        Column {
            Row (verticalAlignment = Alignment.CenterVertically) {
                RankCircle(ranking)
                Spacer(Modifier.size(10.dp))
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RankNameRatingRow(ranking)
                    RankWinRateRow(ranking?.winRate)
                }
                RankCircle(ranking)
            }
            AnimatedVisibility(
                expanded
            ) {
                Row(
                    modifier = Modifier.padding(top = 4.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    if (ranking != null) {
                        listOf(
                            ranking.brawlhallaIdOne,
                            ranking.brawlhallaIdTwo
                        ).forEachIndexed { index, playerId ->
                            Button(onClick = {
                                onRankingAction(RankingAction.SelectRanking(playerId))
                            }) {
                                Text("${stringResource(R.string.player)} ${index + 1}")
                            }
                        }
                    }
                }
            }
        }
    }
}


@PreviewLightDark
@Composable
private fun RankingTeamCardPreview() {
    BrawlhallaTheme {
        Surface {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                RankingTeamCard(
                    ranking = rankingTeamSample.toRankingTeamUi()
                )
                RankingTeamCard(
                    ranking = rankingTeamSample.toRankingTeamUi(),
                    defaultExpanded = true
                )
                RankingTeamCard()
                RankingTeamCard(defaultExpanded = true)
            }
        }
    }
}

internal val rankingTeamSample = Ranking.RankingTeam(
    rank = 1,
    teamName = "kimono+te vagy az??",
    brawlhallaIdOne = 5013091,
    brawlhallaIdTwo = 10241375,
    rating = 2804,
    tier = "Valhallan".toTier(),
    wins = 1004,
    games = 1449,
    region = "EU".toRegion(),
    peakRating = 2804,
)