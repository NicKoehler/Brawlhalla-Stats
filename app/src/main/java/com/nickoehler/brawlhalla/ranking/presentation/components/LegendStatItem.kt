package com.nickoehler.brawlhalla.ranking.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
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
    modifier: Modifier = Modifier,
    onStatDetailAction: () -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    CustomCard(
        onClick = { isExpanded = !isExpanded },
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AnimatedContent(isExpanded, label = legend.legendNameKey) { isOpen ->
            if (isOpen) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.animateContentSize()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LegendImage(
                            legend.legendNameKey,
                            legend.image,
                        )
                        Text(
                            legend.legendNameKey,
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp
                        )
                    }
                    Spacer(Modifier.height(20.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(legend.level.toString())
                        Icon(Icons.Default.KeyboardDoubleArrowRight, null)
                        AnimatedLinearProgressBar(
                            legend.xpPercentage.value,
                            legend.legendNameKey,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(Icons.Default.KeyboardDoubleArrowRight, null)
                        Text(legend.nextLevel.toString())
                    }

                    Spacer(Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("XP")
                        Text(legend.xp.formatted)
                    }


                    CustomLegendField(R.string.damageDealt, legend.damageDealt.formatted)
                    CustomLegendField(R.string.damageTaken, legend.damageTaken.formatted)
                    CustomLegendField(R.string.kos, legend.kos.formatted)
                    CustomLegendField(R.string.falls, legend.falls.formatted)
                    CustomLegendField(R.string.suicides, legend.suicides.formatted)
                    CustomLegendField(R.string.teamKos, legend.teamKos.formatted)


                    CustomLegendField(R.string.games, legend.games.formatted)
                    CustomLegendField(R.string.wins, legend.wins.formatted)
                    CustomLegendField(R.string.damageUnarmed, legend.damageUnarmed.formatted)
                    CustomLegendField(
                        R.string.damageThrownItem,
                        legend.damageThrownItem.formatted
                    )
                    CustomLegendField(
                        R.string.damageWeaponOne,
                        legend.damageWeaponOne.formatted
                    )
                    CustomLegendField(
                        R.string.damageWeaponTwo,
                        legend.damageWeaponTwo.formatted
                    )
                    CustomLegendField(R.string.damageGadgets, legend.damageGadgets.formatted)
                    CustomLegendField(R.string.koUnarmed, legend.koUnarmed.formatted)
                    CustomLegendField(R.string.koThrownItem, legend.koThrownItem.formatted)
                    CustomLegendField(R.string.koWeaponOne, legend.koWeaponOne.formatted)
                    CustomLegendField(R.string.koWeaponTwo, legend.koWeaponTwo.formatted)
                    CustomLegendField(R.string.koGadgets, legend.koGadgets.formatted)
                    CustomLegendField(
                        R.string.timeHeldWeaponOne,
                        legend.timeHeldWeaponOne.formatted,
                    )
                    CustomLegendField(
                        R.string.timeHeldWeaponTwo,
                        legend.timeHeldWeaponTwo.formatted,
                    )

                    Button(
                        onClick = onStatDetailAction
                    ) {
                        Text(stringResource(R.string.goToLegend))
                    }
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.animateContentSize()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LegendImage(
                            legend.legendNameKey,
                            legend.image,
                        )
                        Spacer(Modifier.width(16.dp))
                        Column {
                            Text(legend.legendNameKey)
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(legend.level.toString())
                                Icon(Icons.Default.KeyboardDoubleArrowRight, null)
                                AnimatedLinearProgressBar(
                                    legend.xpPercentage.value,
                                    legend.legendNameKey,
                                    modifier = Modifier.weight(1f)
                                )
                                Icon(Icons.Default.KeyboardDoubleArrowRight, null)
                                Text(legend.nextLevel.toString())
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
private fun LegendStatItemPreview() {

    BrawlhallaTheme {
        Surface {
            LegendStatItem(
                statDetailSample.legends[0].toStatLegendUi()
            ) { }
        }
    }

}