package com.nickoehler.brawlhalla.ranking.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.ranking.presentation.components.ranking_card.RankWinRateRow
import com.nickoehler.brawlhalla.ranking.presentation.components.ranking_card.TierBox
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toRankingTeamUi
import com.nickoehler.brawlhalla.ranking.presentation.screens.rankingDetailSample
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import kotlinx.coroutines.delay

@Composable
fun TeamItem(
    team: RankingUi.RankingTeamUi,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(100L)
        visible = true
    }

    val animatedFloat by animateFloatAsState(if (visible) 1f else 0f)


    CustomCard(
        onClick = onClick,
        modifier = modifier
            .scale(animatedFloat)
            .alpha(animatedFloat),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(team.region.flag)
                Text(
                    team.teamName,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

            }
            RankWinRateRow(team.winRate)
            TierBox(team.rating, team.tier)
        }
    }
}

@Composable
fun TeamItemDetail(
    team: RankingUi.RankingTeamUi,
    columns: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val background = MaterialTheme.colorScheme.surfaceContainerHigh

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {


        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(team.region.flag)
            Text(
                team.teamName,
                maxLines = 1,
                fontSize = 30.sp,
                lineHeight = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        TierBox(team.rating, team.tier)

        RankWinRateRow(team.winRate)

        if (team.brawlhallaIdOne != team.brawlhallaIdTwo) {
            Button(onClick = onClick) {
                Text(
                    stringResource(R.string.goToTeamMate)
                )
            }
        }

        LazyVerticalGrid(
            contentPadding = PaddingValues(8.dp),
            columns = GridCells.Fixed(columns),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                items = listOf(
                    Pair(
                        R.string.rating,
                        team.rating.formatted
                    ),
                    Pair(
                        R.string.peakRating,
                        team.peakRating.formatted
                    ),
                    Pair(
                        R.string.games,
                        team.games.formatted
                    ),
                    Pair(
                        R.string.wins,
                        team.wins.formatted
                    ),
                ), key = { it.first }
            ) { (key, value) ->
                CustomRankingField(
                    key = key,
                    value = value,
                    color = background
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun TeamItemPreview() {
    BrawlhallaTheme {
        Surface {
            TeamItem(
                rankingDetailSample.teams[0].toRankingTeamUi(),
                onClick = {},
                modifier = Modifier.aspectRatio(1f)
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun TeamItemDetailPreview() {
    BrawlhallaTheme {
        Surface {
            TeamItemDetail(
                rankingDetailSample.teams[0].toRankingTeamUi(),
                2,
                onClick = {},
            )
        }
    }
}