package com.nickoehler.brawlhalla.ranking.presentation.screens

import android.widget.Toast
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nickoehler.brawlhalla.core.presentation.UiEvent
import com.nickoehler.brawlhalla.core.presentation.util.toString
import com.nickoehler.brawlhalla.ranking.presentation.RankingAction
import com.nickoehler.brawlhalla.ranking.presentation.RankingViewModel
import com.nickoehler.brawlhalla.ranking.presentation.StatDetailAction
import com.nickoehler.brawlhalla.ranking.presentation.StatDetailViewModel
import com.nickoehler.brawlhalla.ranking.presentation.util.toString
import com.plcoding.cryptotracker.core.presentation.util.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveRankingPane(
    onClanSelection: (Int) -> Unit,
    onLegendSelection: (Int) -> Unit,
    viewModel: RankingViewModel = koinViewModel<RankingViewModel>(),
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val rankingState by viewModel.state.collectAsStateWithLifecycle()
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()

    val statDetailViewModel = koinViewModel<StatDetailViewModel>()
    val statDetailState by statDetailViewModel.state.collectAsStateWithLifecycle()


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
                navigator.navigateTo(
                    ListDetailPaneScaffoldRole.Detail
                )
            }

            is UiEvent.PopBack -> {
                navigator.navigateBack()
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
                        if (action is RankingAction.SelectRanking)
                            navigator.navigateTo(
                                ListDetailPaneScaffoldRole.Detail
                            )
                    },
                )
            }
        },
        detailPane = {
            AnimatedPane {
                StatDetailScreen(
                    rankingState.selectedStatDetailId,
                    statDetailState,
                    onStatDetailAction = { action ->
                        statDetailViewModel.onStatDetailAction(action)
                        if (action is StatDetailAction.SelectClan) {
                            onClanSelection(action.clanId)
                        }
                        if (action is StatDetailAction.SelectLegend) {
                            onLegendSelection(action.legendId)
                        }
                    },
                    onError = {
                        navigator.navigateBack()
                    },
                    events = statDetailViewModel.uiEvents
                )
            }
        },
    )
}