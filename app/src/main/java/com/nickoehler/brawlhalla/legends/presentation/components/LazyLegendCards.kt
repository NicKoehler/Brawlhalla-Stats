package com.nickoehler.brawlhalla.legends.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.core.presentation.WeaponAction
import com.nickoehler.brawlhalla.core.presentation.models.toWeaponUi
import com.nickoehler.brawlhalla.legends.presentation.LegendAction
import com.nickoehler.brawlhalla.legends.presentation.LegendsListState
import com.nickoehler.brawlhalla.legends.presentation.models.FilterOptions
import com.nickoehler.brawlhalla.legends.presentation.models.toLegendUi
import com.nickoehler.brawlhalla.legends.presentation.models.toLocalizedString
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LazyLegendsCards(
    state: LegendsListState,
    modifier: Modifier = Modifier,
    searchBarHeight: Dp,
    lazyColumnState: LazyListState = rememberLazyListState(),
    onLegendAction: (LegendAction) -> Unit = {},
    onWeaponAction: (WeaponAction) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        state = lazyColumnState,
        contentPadding = PaddingValues(bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Spacer(
                if (state.isFilterOpen) Modifier.height(
                    searchBarHeight
                ) else Modifier.height(searchBarHeight - 16.dp)
            )
            AnimatedVisibility(
                state.isFilterOpen,
                label = "openFilters",
                enter = fadeIn() + slideInVertically { -it } + expandVertically(),
                exit = fadeOut() + slideOutVertically { -it } + shrinkVertically()
            ) {
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
                                Text(currentFilter.toLocalizedString(LocalContext.current))
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
            AnimatedVisibility(
                visible = state.isListLoading,
                enter = slideInVertically { -it } + expandVertically(),
                exit = slideOutVertically { -it } + shrinkVertically()
            ) {
                LoadingIndicator()
            }
        }

        if (state.isListLoading) {
            items(50) {
                LegendCard()
            }
        } else {
            items(state.legends, key = { legend -> legend.legendId }) { legend ->
                LegendCard(
                    modifier = Modifier.animateItem(),
                    legend = legend,
                    onLegendAction = onLegendAction,
                    onWeaponAction = onWeaponAction
                )
            }
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
                    isFilterOpen = true,
                    legends = listOf(legendSample.toLegendUi()),
                    weapons = listOf("sword".toWeaponUi()),
                ),
                searchBarHeight = 80.dp
            )
        }
    }
}