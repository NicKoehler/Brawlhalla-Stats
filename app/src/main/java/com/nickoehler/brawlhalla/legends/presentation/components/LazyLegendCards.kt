package com.nickoehler.brawlhalla.legends.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.core.presentation.WeaponAction
import com.nickoehler.brawlhalla.core.presentation.models.toWeaponUi
import com.nickoehler.brawlhalla.legends.presentation.LegendAction
import com.nickoehler.brawlhalla.legends.presentation.LegendsListState
import com.nickoehler.brawlhalla.legends.presentation.mappers.toLocalizedString
import com.nickoehler.brawlhalla.legends.presentation.models.FilterOptions
import com.nickoehler.brawlhalla.legends.presentation.models.toLegendUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun LazyLegendsCards(
    state: LegendsListState,
    onLegendAction: (LegendAction) -> Unit,
    onWeaponAction: (WeaponAction) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            AnimatedContent(
                state.openFilters,
                label = "openFilters",
            ) { open ->
                if (open) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SingleChoiceSegmentedButtonRow(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            FilterOptions.entries.forEachIndexed { index, currentFilter ->
                                SegmentedButton(
                                    selected = state.selectedFilter == currentFilter,
                                    onClick = {
                                        onLegendAction(
                                            LegendAction.SelectFilter(
                                                currentFilter
                                            )
                                        )
                                    },
                                    shape = SegmentedButtonDefaults.itemShape(
                                        index,
                                        FilterOptions.entries.size
                                    )
                                ) {
                                    Text(currentFilter.toLocalizedString())
                                }
                            }
                        }
                        AnimatedContent(
                            state.selectedFilter,
                            label = "selectedFilter"
                        ) { filter ->
                            when (filter) {
                                FilterOptions.WEAPONS -> WeaponsFilter(
                                    state.weapons,
                                    onWeaponAction
                                )

                                FilterOptions.STATS -> StatFilter(
                                    state.selectedStatType,
                                    state.selectedStatValue,
                                    onLegendAction
                                )
                            }
                        }
                    }
                }
            }
        }

        items(state.legends, key = { legend -> legend.legendId }) { legend ->
            LegendCard(legend, onLegendAction, onWeaponAction, modifier = Modifier.animateItem())
        }
    }
}


@PreviewLightDark
@Composable
private fun LazyLegendsCardsPreview() {
    BrawlhallaTheme {
        Surface {
            LazyLegendsCards(
                LegendsListState(
                    openFilters = true,
                    legends = listOf(legendSample.toLegendUi()),
                    weapons = listOf("sword".toWeaponUi()),
                ), {}, {})
        }
    }
}