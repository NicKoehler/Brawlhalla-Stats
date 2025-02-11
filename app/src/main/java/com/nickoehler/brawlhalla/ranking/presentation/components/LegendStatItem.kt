package com.nickoehler.brawlhalla.ranking.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.presentation.components.AnimatedLinearProgressBar
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.core.presentation.components.LegendImage
import com.nickoehler.brawlhalla.ranking.presentation.models.StatLegendUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toStatLegendUi
import com.nickoehler.brawlhalla.ranking.presentation.screens.statDetailSample
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun LegendStatItem(
    legend: StatLegendUi,
    onStatDetailAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    CustomCard(
        modifier = modifier.aspectRatio(1f),
        onClick = onStatDetailAction
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
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        legend.level.formatted,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Text(
                legend.legendNameKey,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )

            AnimatedLinearProgressBar(
                legend.xpPercentage.value,
                legend.legendNameKey,
                modifier = Modifier.width(134.dp)
            )
        }
    }
}


@Composable
fun LegendStatItemDetail(
    legend: StatLegendUi,
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

        CustomLevelProgressBar(
            percentage = legend.xpPercentage.value,
            key = legend.legendNameKey,
            currentLevel = legend.level.value,
            nextLevel = legend.nextLevel?.value
        )

        Text(legend.matchTime.formatted)

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

            item {
                CustomRankingField(
                    R.string.kos,
                    legend.kos.formatted,
                    background
                )
            }
            item {
                CustomRankingField(
                    R.string.falls,
                    legend.falls.formatted,
                    background
                )
            }

            item {
                CustomRankingField(
                    R.string.suicides,
                    legend.suicides.formatted,
                    background
                )
            }
            item {
                CustomRankingField(
                    R.string.teamKos,
                    legend.teamKos.formatted,
                    background
                )
            }

            item {
                CustomRankingField(
                    R.string.damageDealt,
                    legend.damageDealt.formatted,
                    background
                )
            }
            item {
                CustomRankingField(
                    R.string.damageTaken,
                    legend.damageTaken.formatted,
                    background
                )
            }

            item {
                CustomRankingField(
                    R.string.koWeaponOne,
                    legend.koWeaponOne.formatted,
                    background
                )
            }
            item {
                CustomRankingField(
                    R.string.koWeaponTwo,
                    legend.koWeaponTwo.formatted,
                    background
                )
            }

            item {
                CustomRankingField(
                    R.string.damageWeaponOne,
                    legend.damageWeaponOne.formatted,
                    background
                )
            }
            item {
                CustomRankingField(
                    R.string.damageWeaponTwo,
                    legend.damageWeaponTwo.formatted,
                    background
                )
            }

            item {
                CustomRankingField(
                    R.string.timeHeldWeaponOne,
                    legend.timeHeldWeaponOne.formatted,
                    background
                )
            }
            item {
                CustomRankingField(
                    R.string.timeHeldWeaponTwo,
                    legend.timeHeldWeaponTwo.formatted,
                    background
                )
            }

            item { CustomRankingField(R.string.koUnarmed, legend.koUnarmed.formatted, background) }
            item {
                CustomRankingField(
                    R.string.damageUnarmed,
                    legend.damageUnarmed.formatted,
                    background
                )
            }


            item {
                CustomRankingField(
                    R.string.koThrownItem,
                    legend.koThrownItem.formatted,
                    background
                )
            }
            item {
                CustomRankingField(
                    R.string.damageThrownItem,
                    legend.damageThrownItem.formatted,
                    background
                )
            }

            item {
                CustomRankingField(
                    R.string.koGadgets,
                    legend.koGadgets.formatted,
                    background
                )
            }
            item {
                CustomRankingField(
                    R.string.damageGadgets,
                    legend.damageGadgets.formatted,
                    background
                )
            }

        }

    }
}

@Preview
@Composable
private fun LegendStatItemDetailPreview() {
    BrawlhallaTheme {
        Surface {
            LegendStatItemDetail(
                statDetailSample.legends[0].toStatLegendUi(),
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun LegendStatItemPreview() {

    BrawlhallaTheme {
        Surface {
            LegendStatItem(
                statDetailSample.legends[0].toStatLegendUi(),
                onStatDetailAction = {},
            )
        }
    }

}