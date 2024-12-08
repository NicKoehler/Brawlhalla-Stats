package com.nickoehler.brawlhalla.ranking.presentation.screens

import android.widget.Toast
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nickoehler.brawlhalla.core.presentation.UiEvent
import com.nickoehler.brawlhalla.core.presentation.util.toString
import com.nickoehler.brawlhalla.ranking.presentation.RankingAction
import com.nickoehler.brawlhalla.ranking.presentation.RankingViewModel
import com.nickoehler.brawlhalla.ui.Route
import com.plcoding.cryptotracker.core.presentation.util.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveRankingPane(
    ranking: Route.Ranking,
    viewModel: RankingViewModel = koinViewModel<RankingViewModel>(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
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

            is UiEvent.NavigateToDetail -> navigator.navigateTo(
                ListDetailPaneScaffoldRole.Detail
            )

            is UiEvent.NavigateToList -> navigator.navigateTo(
                ListDetailPaneScaffoldRole.List
            )

            else -> {}
        }
    }

    // handle deeplink
    LaunchedEffect(true) {
        if (ranking.id != null) {
            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
            viewModel.onRankingAction(
                RankingAction.SelectRanking(
                    ranking.id
                )
            )
        }
    }

    NavigableListDetailPaneScaffold(
        modifier = modifier,
        navigator = navigator,
        listPane = {
            AnimatedPane {
                RankingListScreen(
                    state,
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
                RankingDetailScreen(
                    state,
                    viewModel::onRankingAction,
                    viewModel::onRankingEvent
                )
//                        state,
//                        onWeaponAction = { action ->
//                            viewModel.onWeaponAction(action)
//                            if (action is WeaponAction.Click)
//                                navigator.navigateBack()
//                        },
//                        onLegendAction = { action ->
//                            viewModel.onLegendAction(action)
//                            if (action is LegendAction.SelectStat)
//                                navigator.navigateBack()
//                        }
            }
        }

    )
}