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
import com.plcoding.cryptotracker.core.presentation.util.ObserveAsEvents
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveLegendsPane(
    modifier: Modifier = Modifier,
    viewModel: LegendsViewModel = koinViewModel<LegendsViewModel>()
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

            is UiEvent.GoToDetail -> {
                coroutineScope.launch {
                    navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                }
            }

            else -> {}
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
                        if (action is LegendAction.SelectLegend) {
                            coroutineScope.launch {
                                navigator.navigateTo(
                                    ListDetailPaneScaffoldRole.Detail
                                )
                            }
                        }
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
                            if (action is WeaponAction.Click) {
                                coroutineScope.launch {
                                    navigator.navigateBack()
                                }
                            }
                        },
                        onLegendAction = { action ->
                            viewModel.onLegendAction(action)
                            if (action is LegendAction.SelectStat) {
                                coroutineScope.launch {
                                    navigator.navigateBack()
                                }
                            }
                        },
                    )
                }
            }
        }
    )
}