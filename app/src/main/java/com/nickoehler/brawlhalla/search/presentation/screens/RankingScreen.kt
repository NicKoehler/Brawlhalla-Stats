package com.nickoehler.brawlhalla.search.presentation.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.search.presentation.RankingAction
import com.nickoehler.brawlhalla.search.presentation.RankingState
import com.nickoehler.brawlhalla.search.presentation.components.RankingCard
import com.nickoehler.brawlhalla.search.presentation.components.RankingTopAppBar
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingScreen(
    state: RankingState,
    onRankingAction: (RankingAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RankingTopAppBar(
                state = state,
                onRankingAction = onRankingAction,
                scrollBehavior = scrollBehavior,
            )
        }
    ) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (state.isListLoading) {
                CircularProgressIndicator()
            } else {
                if (state.players.isEmpty()) {
                    Text(stringResource(R.string.no_players_found))

                } else {
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(it)
                    ) {
                        items(state.players, { ranking -> ranking.brawlhallaId }) { ranking ->
                            RankingCard(
                                ranking = ranking,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateItem()
                            )
                        }
                        if (state.showLoadMore)
                            item {
                                AnimatedContent(
                                    state.isLoadingMore,
                                    label = "isLoadingMore"
                                ) { isLoading ->
                                    if (isLoading) {
                                        CircularProgressIndicator()
                                    } else {
                                        Button(
                                            onClick = { onRankingAction(RankingAction.LoadMore) }
                                        ) {
                                            Text(stringResource(R.string.more))
                                        }
                                    }
                                }

                            }
                        item {

                        }
                    }
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
                {}
            )
        }
    }
}
