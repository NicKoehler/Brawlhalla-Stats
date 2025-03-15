package com.nickoehler.brawlhalla.ranking.presentation.screens

import android.widget.Toast
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nickoehler.brawlhalla.core.presentation.UiEvent
import com.nickoehler.brawlhalla.core.presentation.util.toString
import com.nickoehler.brawlhalla.ranking.presentation.RankingAction
import com.nickoehler.brawlhalla.ranking.presentation.RankingViewModel
import com.nickoehler.brawlhalla.ranking.presentation.StatDetailViewModel
import com.nickoehler.brawlhalla.ranking.presentation.util.toString
import com.plcoding.cryptotracker.core.presentation.util.ObserveAsEvents
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveRankingPane(
    modifier: Modifier = Modifier,
    onClanSelection: (Int) -> Unit,
    onLegendSelection: (Int) -> Unit,
    onPlayerSelection: (Int) -> Unit,
    viewModel: RankingViewModel = koinViewModel<RankingViewModel>(),
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val rankingState by viewModel.state.collectAsStateWithLifecycle()
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()

    ObserveAsEvents(viewModel.uiEvents) { event ->
        when (event) {
            is UiEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.toString(context),
                    Toast.LENGTH_LONG
                ).show()
            }

            is UiEvent.Message -> {
                Toast.makeText(
                    context,
                    event.message.toString(context),
                    Toast.LENGTH_SHORT
                ).show()
            }

            is UiEvent.GoToDetail -> {
                coroutineScope.launch {
                    navigator.navigateTo(
                        ListDetailPaneScaffoldRole.Detail
                    )
                }
            }

            is UiEvent.PopBack -> {
                coroutineScope.launch {
                    navigator.navigateBack()
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
                RankingListScreen(
                    rankingState,
                    onAppBarAction = viewModel::onAppBarAction,
                    onRankingAction = { action ->
                        viewModel.onRankingAction(action)
                        if (action is RankingAction.SelectRanking) {
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
                if (rankingState.selectedStatDetailId != null) {

                    val statDetailViewModel = koinViewModel<StatDetailViewModel>(
                        key = rankingState.selectedStatDetailId.toString(),
                        parameters = { parametersOf(rankingState.selectedStatDetailId) }
                    )
                    val statDetailState by statDetailViewModel.state.collectAsStateWithLifecycle()

                    StatDetailScreen(
                        statDetailState,
                        onPlayerSelection = onPlayerSelection,
                        onClanSelection = onClanSelection,
                        onStatDetailAction = statDetailViewModel::onStatDetailAction,
                        onBack = {
                            coroutineScope.launch {
                                navigator.navigateBack()
                            }
                        },
                        events = statDetailViewModel.uiEvents
                    )
                }
            }
        },
    )
}