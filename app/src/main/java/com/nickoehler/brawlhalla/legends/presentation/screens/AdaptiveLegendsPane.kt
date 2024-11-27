package com.nickoehler.brawlhalla.legends.presentation.screens

import LegendsEvent
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
import com.nickoehler.brawlhalla.core.presentation.WeaponAction
import com.nickoehler.brawlhalla.core.presentation.util.toString
import com.nickoehler.brawlhalla.legends.presentation.LegendAction
import com.nickoehler.brawlhalla.legends.presentation.LegendsViewModel
import com.plcoding.cryptotracker.core.presentation.util.ObserveAsEvents

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveLegendsPane(
    viewModel: LegendsViewModel,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is LegendsEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.toString(context),
                    Toast.LENGTH_LONG
                ).show()
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
                    onWeaponAction = viewModel::onWeaponAction,
                    onLegendAction = { action ->
                        viewModel.onLegendAction(action)
                        when (action) {
                            is LegendAction.SelectLegend -> navigator.navigateTo(
                                ListDetailPaneScaffoldRole.Detail
                            )

                            else -> {}
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
                            if (action is WeaponAction.Click)
                                navigator.navigateBack()
                        },
                        onLegendAction = { action ->
                            viewModel.onLegendAction(action)
                            if (action is LegendAction.SelectStat)
                                navigator.navigateBack()
                        }
                    )
                }
            }
        }

    )
}