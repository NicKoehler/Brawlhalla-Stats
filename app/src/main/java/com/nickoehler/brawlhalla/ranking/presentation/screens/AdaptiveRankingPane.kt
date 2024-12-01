package com.nickoehler.brawlhalla.ranking.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nickoehler.brawlhalla.core.presentation.ErrorEvent
import com.nickoehler.brawlhalla.core.presentation.util.toString
import com.nickoehler.brawlhalla.ranking.presentation.RankingAction
import com.nickoehler.brawlhalla.ranking.presentation.RankingViewModel
import com.plcoding.cryptotracker.core.presentation.util.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveRankingPane(
    viewModel: RankingViewModel = koinViewModel<RankingViewModel>(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()

    ObserveAsEvents(viewModel.events) { event ->
        if (event is ErrorEvent.Error) {
            Toast.makeText(
                context,
                event.error.toString(context),
                Toast.LENGTH_LONG
            ).show()
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
                        println(action)
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
                Box(contentAlignment = Alignment.Center) {
                    RankingDetailScreen(
                        state,

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
        }

    )
}