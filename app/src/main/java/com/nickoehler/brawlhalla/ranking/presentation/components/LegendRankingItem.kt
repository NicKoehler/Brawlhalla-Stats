package com.nickoehler.brawlhalla.ranking.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.core.presentation.components.LegendImage
import com.nickoehler.brawlhalla.ranking.presentation.components.ranking_card.RankWinRateRow
import com.nickoehler.brawlhalla.ranking.presentation.components.ranking_card.TierBox
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingLegendUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toRankingLegendUi
import com.nickoehler.brawlhalla.ranking.presentation.screens.rankingDetailSample
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun LegendRankingItem(
    legend: RankingLegendUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CustomCard(
        onClick = onClick,
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .aspectRatio(1f),
                contentAlignment = Alignment.BottomEnd
            ) {
                LegendImage(
                    legend.legendNameKey,
                    legend.image,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                )
                TierBox(legend.rating, legend.tier)
            }
            Column(
                modifier = Modifier.height(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    legend.legendNameKey,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
                if (legend.winRate != null) {
                    RankWinRateRow(legend.winRate)
                }
            }
        }
    }
}


@Composable
fun LegendRankingItemDetail(
    legend: RankingLegendUi,
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

        Text(
            legend.legendNameKey,
            fontSize = 30.sp,
            lineHeight = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        TierBox(legend.rating, legend.tier)

        if (legend.winRate != null) {
            RankWinRateRow(legend.winRate)
        }

        Button(onClick = onClick) {
            Text(
                stringResource(R.string.goToLegend, legend.legendNameKey)
            )
        }

        LazyVerticalGrid(
            contentPadding = PaddingValues(8.dp),
            columns = GridCells.Fixed(columns),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item { CustomRankingField(R.string.rating, legend.rating.formatted, background) }
            item {
                CustomRankingField(
                    R.string.peakRating,
                    legend.peakRating.formatted,
                    background
                )
            }
            item { CustomRankingField(R.string.games, legend.games.formatted, background) }
            item { CustomRankingField(R.string.wins, legend.wins.formatted, background) }
        }
    }
}

@Preview
@Composable
private fun LegendRankingItemDetailPreview() {
    BrawlhallaTheme {
        Surface {
            LegendRankingItemDetail(
                rankingDetailSample.legends[0].toRankingLegendUi(),
                2,
                {}
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun LegendRankingItemPreview() {

    BrawlhallaTheme {
        Surface {
            LegendRankingItem(
                rankingDetailSample.legends[0].toRankingLegendUi(),
                onClick = {},
            )
        }
    }

}