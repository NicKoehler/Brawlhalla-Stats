package com.nickoehler.brawlhalla.ranking.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.ranking.presentation.components.ranking_card.RankWinRateRow
import com.nickoehler.brawlhalla.ranking.presentation.components.ranking_card.TierBox
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toLocalizedString
import com.nickoehler.brawlhalla.ranking.presentation.models.toRankingTeamUi
import com.nickoehler.brawlhalla.ranking.presentation.screens.rankingDetailSample
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun TeamItem(
    team: RankingUi.RankingTeamUi,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    CustomCard(
        onClick = onClick,
        modifier = modifier.aspectRatio(1f),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(team.region.flag)
                Text(
                    team.teamName,
                    textAlign = TextAlign.Center,
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
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val background = MaterialTheme.colorScheme.surfaceContainerHigh

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            team.teamName,
            fontSize = 30.sp,
            lineHeight = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        RankWinRateRow(team.winRate)

        Button(onClick = onClick) {
            Text(
                stringResource(R.string.goToTeamMate)
            )
        }

        LazyVerticalGrid(
            contentPadding = PaddingValues(8.dp),
            columns = GridCells.Adaptive(150.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            item { CustomRankingField(R.string.rank, team.rank.formatted, background) }
            item { CustomRankingField(R.string.rating, team.rating.formatted, background) }
            item {
                CustomRankingField(
                    R.string.tier,
                    team.tier.toLocalizedString(context),
                    background
                )
            }
            item { CustomRankingField(R.string.games, team.games.formatted, background) }
            item { CustomRankingField(R.string.wins, team.wins.formatted, background) }
            item { CustomRankingField(R.string.winRate, team.winRate.formatted, background) }
            item { CustomRankingField(R.string.losses, team.losses.formatted, background) }
            item { CustomRankingField(R.string.region, team.region.flag, background) }
            item { CustomRankingField(R.string.peakRating, team.peakRating.formatted, background) }
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
                onClick = {},
            )
        }
    }
}