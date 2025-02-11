package com.nickoehler.brawlhalla.ranking.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.core.presentation.components.LegendImage
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
        modifier = modifier.aspectRatio(1f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.BottomEnd
            ) {
                LegendImage(
                    legend.legendNameKey,
                    legend.image,
                    modifier = Modifier
                        .size(150.dp)
                        .padding(8.dp),
                )
                TierBox(legend.rating, legend.tier)
            }

            Text(
                legend.legendNameKey,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}


@Composable
fun LegendRankingItemDetail(
    legend: RankingLegendUi,
    modifier: Modifier = Modifier,
) {
    val background = MaterialTheme.colorScheme.surfaceContainerHigh

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            legend.legendNameKey,
            fontSize = 30.sp,
            lineHeight = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        // TODO
//        CustomLevelProgressBar(
//            percentage = legend.xpPercentage.value,
//            key = legend.legendNameKey,
//            currentLevel = legend.level.value,
//            nextLevel = legend.nextLevel?.value
//        )


        LazyVerticalGrid(
            contentPadding = PaddingValues(8.dp),
            columns = GridCells.Adaptive(150.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                CustomRankingField(
                    R.string.games,
                    legend.games.formatted,
                    background
                )
            }
            item {
                CustomRankingField(
                    R.string.wins,
                    legend.wins.formatted,
                    background
                )
            }


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