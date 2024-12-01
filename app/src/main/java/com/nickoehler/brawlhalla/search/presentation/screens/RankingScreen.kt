package com.nickoehler.brawlhalla.search.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.presentation.AppBarAction
import com.nickoehler.brawlhalla.core.presentation.components.CustomTopAppBar
import com.nickoehler.brawlhalla.core.presentation.components.ShimmerEffect
import com.nickoehler.brawlhalla.search.presentation.RankingAction
import com.nickoehler.brawlhalla.search.presentation.RankingState
import com.nickoehler.brawlhalla.search.presentation.components.RankingCard
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingScreen(
    state: RankingState,
    onRankingAction: (RankingAction) -> Unit,
    onAppBarAction: (AppBarAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())


    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
//            RankingTopAppBar(
//                state = state,
//                onRankingAction = onRankingAction,
//                scrollBehavior = scrollBehavior,
//            )
            CustomTopAppBar(
                stringResource(R.string.rankings),
                state = state.appBarState,
                scrollBehavior = scrollBehavior,
                onAppBarAction = onAppBarAction,
            )
        }
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (state.isListLoading) {
                items(50, key = { key -> key }) {
                    ShimmerEffect(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp)
                            .clip(CircleShape)
                    )
                }
            } else if (state.players.isNotEmpty()) {
                items(state.players, { ranking -> ranking.rank.value }) { ranking ->
                    RankingCard(
                        ranking = ranking,
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItem()
                    )
                }
                item {
                    if (state.isLoadingMore) {
                        ShimmerEffect(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(75.dp)
                                .clip(CircleShape)
                        )
                    }
                }

                item {
                    if (state.showLoadMore) {
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
            RankingScreen(
                state = RankingState(),
                {},
                {}
            )
        }
    }
}
