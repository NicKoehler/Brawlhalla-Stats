package com.nickoehler.brawlhalla.ranking.presentation.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.presentation.UiEvent
import com.nickoehler.brawlhalla.core.presentation.components.CustomFloatingActionButton
import com.nickoehler.brawlhalla.core.presentation.components.SimpleSearchBar
import com.nickoehler.brawlhalla.core.presentation.models.toDisplayableNumber
import com.nickoehler.brawlhalla.core.presentation.util.ObserveAsEvents
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
import com.nickoehler.brawlhalla.ranking.presentation.util.toString
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import com.nickoehler.brawlhalla.ui.theme.Spacing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun RankingListScreen(
    state: RankingState,
    events: Flow<UiEvent>,
    modifier: Modifier = Modifier,
    onRankingAction: (RankingAction) -> Unit = {},
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    val coroutineScope = rememberCoroutineScope()
    val gridState = rememberLazyStaggeredGridState()
    val toolbarHeight = 80.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
    var toolbarOffsetHeightPx by remember { mutableFloatStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx + delta
                toolbarOffsetHeightPx = newOffset.coerceIn(-toolbarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }
    val searchBarHeight by remember {
        derivedStateOf {
            toolbarHeight + toolbarOffsetHeightPx.dp
        }
    }
    val lazyColumnState = rememberLazyListState()

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val height by animateDpAsState(if (state.isFilterOpen) searchBarHeight else searchBarHeight - 16.dp)

    ObserveAsEvents(events) { event ->
        when (event) {
            is UiEvent.Message -> Toast.makeText(
                context,
                event.message.toString(context),
                Toast.LENGTH_SHORT
            ).show()

            is UiEvent.Error ->
                Toast.makeText(
                    context,
                    event.error.toString(),
                    Toast.LENGTH_SHORT
                ).show()

            else -> {}
        }
    }

    LaunchedEffect(lazyColumnState.isScrollInProgress) {
        focusManager.clearFocus()
    }

    Scaffold(
        modifier = modifier
            .safeDrawingPadding()
            .nestedScroll(nestedScrollConnection),
        floatingActionButton = {
            CustomFloatingActionButton(lazyColumnState)
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .semantics { isTraversalGroup = true }
                .zIndex(1f)
                .offset {
                    IntOffset(
                        x = 0,
                        y = toolbarOffsetHeightPx.roundToInt()
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            SimpleSearchBar(
                query = state.searchQuery,
                focusManager = focusManager,
                focusRequester = focusRequester,
                lazyColumnState = lazyColumnState,
                isFilterOpen = state.isFilterOpen,
                onFilterToggle = {
                    onRankingAction(RankingAction.OnFilterToggle)
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                },
                onSearch = {
                    onRankingAction(RankingAction.Search(state.searchQuery))
                },
                onQueryChange = {
                    onRankingAction(RankingAction.QueryChange(it))
                },
                placeholder = {
                    Text(
                        stringResource(
                            if (state.selectedBracket != Bracket.TWO_VS_TWO) {
                                R.string.search_name_or_id
                            } else {
                                R.string.search_cant_search
                            }
                        )
                    )
                },
                enabled = state.selectedBracket != Bracket.TWO_VS_TWO,
                modifier = Modifier
                    .padding(horizontal = Spacing.scaffoldWindowInsets)
            )
        }

        LazyColumn(
            state = lazyColumnState,
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(horizontal = Spacing.scaffoldWindowInsets)
        ) {

            item {
                Spacer(Modifier.height(height))
                AnimatedVisibility(
                    state.isFilterOpen,
                    label = "openFilters",
                    enter = fadeIn() + slideInVertically { -it } + expandVertically(),
                    exit = fadeOut() + slideOutVertically { -it } + shrinkVertically()
                ) {
                    Column {
                        LazyHorizontalStaggeredGrid(
                            state = gridState,
                            modifier = Modifier
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
                                    enabled = !state.isListLoading,
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
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 40.dp, max = 100.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            items(Bracket.entries.sortedBy { it != state.selectedBracket }
                                .filter { if (state.searchedQuery.isNotBlank()) it != Bracket.TWO_VS_TWO else true }
                                .map {
                                    it.toBracketUi()
                                }, { it.value }) {
                                FilterChip(
                                    state.selectedBracket == it.value,
                                    enabled = !state.isListLoading,
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
                }
                if (state.searchedQuery.isNotBlank()) {
                    Row(Modifier.fillMaxWidth()) {
                        AssistChip(
                            enabled = !state.isListLoading,
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Cancel,
                                    stringResource(R.string.cancel)
                                )
                            },
                            onClick = { onRankingAction(RankingAction.ResetSearch) },
                            label = {
                                Text(state.searchedQuery)
                            }
                        )
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
                items(10) {
                    RankingCard(
                        modifier = Modifier
                            .animateItem()
                            .fillMaxWidth(),
                        selectedBracket = state.selectedBracket.toBracketUi()
                    )
                }
            } else if (state.searchedQuery.isNotBlank()) {
                if (state.searchResults.isNotEmpty()) {
                    items(state.searchResults, { ranking ->
                        when (ranking) {
                            is RankingUi.RankingSoloUi -> ranking.rank.value
                            is RankingUi.RankingTeamUi -> ranking.rank.value
                        }
                    }) { ranking ->
                        RankingCard(
                            ranking = ranking,
                            modifier = Modifier
                                .animateItem()
                                .fillMaxWidth(),
                            onRankingAction = onRankingAction
                        )
                    }
                } else {
                    item {
                        Text(stringResource(R.string.no_players_found))
                    }
                }
            } else if (state.players.isNotEmpty()) {
                items(
                    state.players, { ranking ->
                        when (ranking) {
                            is RankingUi.RankingSoloUi -> ranking.rank.value
                            is RankingUi.RankingTeamUi -> ranking.rank.value
                        }
                    }) { ranking ->
                    RankingCard(
                        ranking = ranking,
                        modifier = Modifier
                            .animateItem()
                            .fillMaxWidth(),
                        onRankingAction = onRankingAction
                    )
                }
                item {
                    if (state.isLoadingMore) {
                        RankingCard(
                            modifier = Modifier
                                .animateItem()
                                .fillMaxWidth(),
                        )
                    }
                }

                item {
                    if (state.shouldLoadMore) {
                        LaunchedEffect(Unit) {
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
                events = emptyFlow()
            )
        }
    }
}
