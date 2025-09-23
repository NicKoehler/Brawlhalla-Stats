package com.nickoehler.brawlhalla.legends.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.presentation.UiEvent
import com.nickoehler.brawlhalla.core.presentation.WeaponAction
import com.nickoehler.brawlhalla.core.presentation.components.CustomFloatingActionButton
import com.nickoehler.brawlhalla.core.presentation.components.SimpleSearchBar
import com.nickoehler.brawlhalla.core.presentation.util.ObserveAsEvents
import com.nickoehler.brawlhalla.legends.presentation.LegendAction
import com.nickoehler.brawlhalla.legends.presentation.LegendsListState
import com.nickoehler.brawlhalla.legends.presentation.components.LazyLegendsCards
import com.nickoehler.brawlhalla.legends.presentation.components.legendSample
import com.nickoehler.brawlhalla.legends.presentation.models.toLegendUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import com.nickoehler.brawlhalla.ui.theme.Spacing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LegendListScreen(
    state: LegendsListState,
    events: Flow<UiEvent>,
    modifier: Modifier = Modifier,
    lazyColumnState: LazyListState = rememberLazyListState(),
    onLegendAction: (LegendAction) -> Unit,
    onWeaponAction: (WeaponAction) -> Unit,
) {
    val toolbarHeight = 80.dp
    val coroutineScope = rememberCoroutineScope()
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
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val searchBarHeight by remember {
        derivedStateOf {
            toolbarHeight + toolbarOffsetHeightPx.dp
        }
    }

    ObserveAsEvents(events) { event ->
        if (event is UiEvent.ScrollToTop) {
            coroutineScope.launch {
                lazyColumnState.animateScrollToItem(0)
            }
        }
    }

    LaunchedEffect(lazyColumnState.isScrollInProgress) {
        focusManager.clearFocus()
    }

    Scaffold(
        floatingActionButton = {
            CustomFloatingActionButton(lazyColumnState)
        },
        modifier = modifier
            .safeDrawingPadding()
            .nestedScroll(nestedScrollConnection),
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
                isFilterEnabled = state.searchQuery.isBlank(),
                onFilterToggle = {
                    onLegendAction(LegendAction.OnFilterToggle)
                    coroutineScope.launch {
                        lazyColumnState.animateScrollToItem(0)
                    }
                },
                onQueryChange = {
                    onLegendAction(LegendAction.QueryChange(it))
                },
                placeholder = {
                    Text(
                        stringResource(R.string.search)
                    )
                },
                modifier = Modifier.padding(horizontal = Spacing.scaffoldWindowInsets)
            )
        }
        LazyLegendsCards(
            state = state,
            modifier = Modifier
                .padding(horizontal = Spacing.scaffoldWindowInsets),
            lazyColumnState = lazyColumnState,
            onLegendAction = onLegendAction,
            onWeaponAction = onWeaponAction,
            searchBarHeight = searchBarHeight
        )
    }
}


@PreviewLightDark
@Composable
private fun LegendListScreenPreview() {
    BrawlhallaTheme {
        Surface {
            LegendListScreen(
                LegendsListState(
                    legends = (0L..100L).map {
                        legendSample.copy(legendId = it).toLegendUi()
                    },
                ),
                events = emptyFlow(),
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                onLegendAction = {},
                onWeaponAction = {},
            )
        }
    }
}

