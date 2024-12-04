package com.nickoehler.brawlhalla.ranking.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.presentation.AppBarAction
import com.nickoehler.brawlhalla.core.presentation.components.CustomFloatingActionButton
import com.nickoehler.brawlhalla.core.presentation.components.CustomTopAppBar
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableNumber
import com.nickoehler.brawlhalla.ranking.domain.Bracket
import com.nickoehler.brawlhalla.ranking.domain.Region
import com.nickoehler.brawlhalla.ranking.presentation.RankingAction
import com.nickoehler.brawlhalla.ranking.presentation.RankingState
import com.nickoehler.brawlhalla.ranking.presentation.components.ranking_card.RankingCard
import com.nickoehler.brawlhalla.ranking.presentation.components.ranking_card.rankingSoloSample
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toBracketUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toRankingSoloUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toRegionUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingListScreen(
    state: RankingState,
    onRankingAction: (RankingAction) -> Unit = {},
    onAppBarAction: (AppBarAction) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val gridState = rememberLazyStaggeredGridState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val lazyColumnState = rememberLazyListState()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                stringResource(R.string.rankings),
                placeholder = stringResource(R.string.search_name_brawlhalla_id),
                state = state.appBarState,
                scrollBehavior = scrollBehavior,
                onAppBarAction = onAppBarAction,
                showSearch = state.selectedBracket != Bracket.TWO_VS_TWO,
            )
        },
        floatingActionButton = {
            CustomFloatingActionButton(lazyColumnState)
        }
    ) { padding ->
        LazyColumn(
            state = lazyColumnState,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            item {
                LazyHorizontalStaggeredGrid(
                    state = gridState,
                    modifier = modifier
                        .fillMaxWidth()
                        .heightIn(min = 40.dp, max = 65.dp),
                    rows = StaggeredGridCells.FixedSize(30.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalItemSpacing = 4.dp,
                ) {
                    items(Region.entries.filter { it != Region.UNKNOWN }
                        .sortedBy { it != state.selectedRegion }.map {
                            it.toRegionUi()
                        }, { it.value }) {
                        FilterChip(
                            state.selectedRegion == it.value,
                            onClick = {
                                onRankingAction(
                                    RankingAction.SelectRegion(it.value)
                                )
                                coroutineScope.launch {
                                    gridState.animateScrollToItem(index = 0)
                                }
                            },
                            label = { Text(it.toString(context)) },
                            modifier = Modifier.animateItem()
                        )
                    }
                }
            }
            item {
                LazyRow(
                    modifier = modifier
                        .fillMaxWidth()
                        .heightIn(min = 40.dp, max = 100.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(Bracket.entries.sortedBy { it != state.selectedBracket }.map {
                        it.toBracketUi()
                    }, { it.value }) {
                        FilterChip(
                            state.selectedBracket == it.value,
                            onClick = {
                                onRankingAction(
                                    RankingAction.SelectBracket(
                                        it.value
                                    )
                                )
                            },
                            label = { Text(it.toString(context)) },
                            modifier = Modifier.animateItem()
                        )
                    }
                }
            }
            if (state.isListLoading) {
                items(50) {
                    RankingCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItem(),
                        selectedBracket = state.selectedBracket.toBracketUi()
                    )
                }
            } else if (state.players.isNotEmpty()) {
                items(state.players, { ranking ->
                    when (ranking) {
                        is RankingUi.RankingSoloUi -> ranking.rank.value
                        is RankingUi.RankingTeamUi -> ranking.rank.value
                    }
                }) { ranking ->
                    RankingCard(
                        ranking = ranking,
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItem(),
                        onRankingAction = onRankingAction
                    )
                }
                item {
                    if (state.isLoadingMore) {
                        RankingCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItem(),
                        )
                    }
                }

                item {
                    if (state.shouldLoadMore) {
                        LaunchedEffect(true) {
                            onRankingAction(RankingAction.LoadMore)
                        }
                    }
                }

            } else {
                item {
                    Text(stringResource(R.string.no_players_found))
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun SearchScreenPreview() {
    BrawlhallaTheme {
        Surface {
            RankingListScreen(
                state = RankingState(
                    players = (1..50).map {
                        rankingSoloSample.toRankingSoloUi().copy(rank = it.toDisplayableNumber())
                    }
                ),
            )
        }
    }
}
