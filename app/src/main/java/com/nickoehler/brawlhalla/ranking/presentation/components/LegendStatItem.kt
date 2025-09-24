package com.nickoehler.brawlhalla.ranking.presentation.components

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
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
import kotlinx.coroutines.delay

@Composable
fun LegendStatItem(
    legend: StatLegendUi,
    onStatDetailAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(100L)
        visible = true
    }

    val animatedFloat by animateFloatAsState(if (visible) 1f else 0f)

    CustomCard(
        modifier = modifier
            .scale(animatedFloat)
            .alpha(animatedFloat),
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
                        .aspectRatio(1f)
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
    columns: Int,
    onClick: () -> Unit,
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
            items(
                items = listOf(
                    Pair(
                        R.string.games,
                        legend.games.formatted
                    ),
                    Pair(
                        R.string.wins,
                        legend.wins.formatted
                    ),
                    Pair(
                        R.string.kos,
                        legend.kos.formatted
                    ),
                    Pair(
                        R.string.falls,
                        legend.falls.formatted
                    ),
                    Pair(
                        R.string.suicides,
                        legend.suicides.formatted
                    ),
                    Pair(
                        R.string.teamKos,
                        legend.teamKos.formatted
                    ),
                    Pair(
                        R.string.damageDealt,
                        legend.damageDealt.formatted
                    ),
                    Pair(
                        R.string.damageTaken,
                        legend.damageTaken.formatted
                    ),
                    Pair(
                        R.string.koWeaponOne,
                        legend.koWeaponOne.formatted
                    ),
                    Pair(
                        R.string.koWeaponTwo,
                        legend.koWeaponTwo.formatted
                    ),
                    Pair(
                        R.string.damageWeaponOne,
                        legend.damageWeaponOne.formatted
                    ),
                    Pair(
                        R.string.damageWeaponTwo,
                        legend.damageWeaponTwo.formatted
                    ),
                    Pair(
                        R.string.timeHeldWeaponOne,
                        legend.timeHeldWeaponOne.formatted
                    ),
                    Pair(
                        R.string.timeHeldWeaponTwo,
                        legend.timeHeldWeaponTwo.formatted
                    ),
                    Pair(
                        R.string.koUnarmed,
                        legend.koUnarmed.formatted
                    ),
                    Pair(
                        R.string.damageUnarmed,
                        legend.damageUnarmed.formatted
                    ),
                    Pair(
                        R.string.koThrownItem,
                        legend.koThrownItem.formatted
                    ),
                    Pair(
                        R.string.damageThrownItem,
                        legend.damageThrownItem.formatted
                    ),
                    Pair(
                        R.string.koGadgets,
                        legend.koGadgets.formatted
                    ),
                    Pair(
                        R.string.damageGadgets,
                        legend.damageGadgets.formatted
                    ),
                ),
                key = { it.first }
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

@Preview
@Composable
private fun LegendStatItemDetailPreview() {
    BrawlhallaTheme {
        Surface {
            LegendStatItemDetail(
                statDetailSample.legends[0].toStatLegendUi(),
                2,
                {}
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