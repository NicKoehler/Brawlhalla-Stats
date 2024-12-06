package com.nickoehler.brawlhalla.legends.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nickoehler.brawlhalla.core.presentation.UiEvent
import com.nickoehler.brawlhalla.core.presentation.WeaponAction
import com.nickoehler.brawlhalla.core.presentation.util.toString
import com.nickoehler.brawlhalla.legends.presentation.LegendAction
import com.nickoehler.brawlhalla.legends.presentation.LegendsViewModel
import com.nickoehler.brawlhalla.ui.Route
import com.plcoding.cryptotracker.core.presentation.util.ObserveAsEvents
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveLegendsPane(
    legend: Route.Legend,
    viewModel: LegendsViewModel = koinViewModel<LegendsViewModel>(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    val lazyListState = rememberLazyListState()

    ObserveAsEvents(viewModel.uiEvents) { event ->
        when (event) {
            is UiEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.toString(context),
                    Toast.LENGTH_LONG
                ).show()
            }

            is UiEvent.ScrollToTop -> {
                coroutineScope.launch {
                    lazyListState.animateScrollToItem(0)
                }
            }

            is UiEvent.NavigateToDetail -> {
                navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
            }

            is UiEvent.NavigateToList -> {
                navigator.navigateTo(ListDetailPaneScaffoldRole.List)
            }
        }
    }

    // handle deeplink
    LaunchedEffect(true) {
        if (legend.id != null) {
            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
            viewModel.onLegendAction(
                LegendAction.SelectLegend(
                    legend.id
                )
            )
        }
    }

    NavigableListDetailPaneScaffold(
        modifier = modifier,
        navigator = navigator,
        listPane = {
            AnimatedPane {
                LegendListScreen(
                    state,
                    lazyListState = lazyListState,
                    onWeaponAction = viewModel::onWeaponAction,
                    onAppBarAction = viewModel::onAppBarAction,
                    onLegendAction = { action ->
                        viewModel.onLegendAction(action)
                        if (action is LegendAction.SelectLegend)
                            navigator.navigateTo(
                                ListDetailPaneScaffoldRole.Detail
                            )
                    },
                )
            }
        },
        detailPane = {
            AnimatedPane {
                Box(contentAlignment = Alignment.Center) {
                    LegendDetailScreen(
                        state,
                        onWeaponAction = { action ->
                            viewModel.onWeaponAction(action)
                            if (action is WeaponAction.Click)
                                navigator.navigateBack()
                        },
                        onLegendAction = { action ->
                            viewModel.onLegendAction(action)
                            if (action is LegendAction.SelectStat)
                                navigator.navigateBack()
                        },
                        uiEvent = viewModel::onUiEvent
                    )
                }
            }
        }
    )
}