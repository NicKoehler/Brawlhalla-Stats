package com.nickoehler.brawlhalla.ranking.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.presentation.AppBarAction
import com.nickoehler.brawlhalla.core.presentation.components.CustomDropdownMenu
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingListScreen(
    state: RankingState,
    onRankingAction: (RankingAction) -> Unit = {},
    onAppBarAction: (AppBarAction) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                stringResource(R.string.rankings),
                state = state.appBarState,
                scrollBehavior = scrollBehavior,
                onAppBarAction = onAppBarAction,
            )
        }
    ) { padding ->
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (state.isListLoading) {
                items(50) {
                    RankingCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItem(),
                        selectedBracket = state.selectedBracket
                    )
                }
            } else if (state.players.isNotEmpty()) {

                item {
                    var isRegionSelectorOpen by remember {
                        mutableStateOf(false)
                    }
                    var isBracketSelectorOpen by remember {
                        mutableStateOf(false)
                    }
                    Row(
                        modifier.fillParentMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        CustomDropdownMenu(
                            stringResource(R.string.region),
                            state.selectedRegion,
                            items = Region.entries.filter { it != Region.UNKNOWN }
                                .map { it.toRegionUi() },
                            onSelect = { selection ->
                                onRankingAction(
                                    RankingAction.SelectRegion(
                                        selection
                                    )
                                )
                            }
                        )
                        CustomDropdownMenu(
                            stringResource(R.string.bracket),
                            state.selectedBracket,
                            items = Bracket.entries.map { it.toBracketUi() },
                            onSelect = { selection ->
                                onRankingAction(
                                    RankingAction.SelectBracket(selection)
                                )
                            }
                        )
                    }
                }

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
