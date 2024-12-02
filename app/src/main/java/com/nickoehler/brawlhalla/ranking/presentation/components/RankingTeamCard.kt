package com.nickoehler.brawlhalla.ranking.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    modifier: Modifier = Modifier
) {
    CustomCard(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        contentPadding = 10.dp,
    ) {
        Box(
            Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceBright),
            contentAlignment = Alignment.Center
        ) {
            if (ranking != null) {
                Text(ranking.rank.formatted)
            }
        }

        Column {
            if (ranking != null) {
                Text(ranking.teamName)
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                if (ranking != null) {
                    listOf(
                        ranking.brawlhallaIdOne,
                        ranking.brawlhallaIdTwo
                    ).forEachIndexed { index, id ->
                        Button({
                            onRankingAction(
                                RankingAction.SelectRanking(
                                    id
                                )
                            )
                        }) {
                            Text("${stringResource(R.string.player)} ${index + 1}")
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
            RankingTeamCard(
                ranking = rankingTeamSample.toRankingTeamUi()
            )
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