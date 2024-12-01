package com.nickoehler.brawlhalla

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.nickoehler.brawlhalla.legends.presentation.LegendAction
import com.nickoehler.brawlhalla.legends.presentation.LegendsViewModel
import com.nickoehler.brawlhalla.legends.presentation.screens.AdaptiveLegendsPane
import com.nickoehler.brawlhalla.ranking.presentation.screens.AdaptiveRankingPane
import com.nickoehler.brawlhalla.ui.Route
import com.nickoehler.brawlhalla.ui.Screens
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()

            val navBackStackEntry = navController.currentBackStackEntryAsState().value
            val currentDestination = navBackStackEntry?.destination

            enableEdgeToEdge()
            BrawlhallaTheme {
                NavigationSuiteScaffold(
                    modifier = Modifier.imePadding(),
                    navigationSuiteItems = {
                        Screens.entries.forEach { currentScreen ->
                            if (currentDestination != null) {
                                val isSelected = currentDestination.hierarchy.any {
                                    it.hasRoute(route = currentScreen.route::class)
                                }
                                item(
                                    selected = isSelected,
                                    onClick = {
                                        if (currentDestination != currentScreen.route) {
                                            navController.navigate(currentScreen.route) {
                                                popUpTo(navController.graph.startDestinationId)
                                                {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            if (isSelected) currentScreen.selectedIcon
                                            else currentScreen.unselectedIcon,
                                            currentScreen.title,
                                        )
                                    },
                                    label = {
                                        if (isSelected) {
                                            Text(currentScreen.title)
                                        }
                                    }
                                )
                            }
                        }

                    }
                ) {
                    NavHost(
                        modifier = Modifier
                            .safeDrawingPadding()
                            .padding(horizontal = 8.dp),
                        navController = navController,
                        startDestination = Route.Legend(),
                    ) {
                        composable<Route.Search> {
                            AdaptiveRankingPane()
                        }
                        composable<Route.Legend>(
                            deepLinks = listOf(
                                navDeepLink<Route.Legend>("bh://legend")
                            )
                        ) {
                            val legendsViewModel by viewModel<LegendsViewModel>()

                            val legend = it.toRoute<Route.Legend>()
                            if (legend.id != null) {
                                legendsViewModel.onLegendAction(LegendAction.SelectLegend(legendId = legend.id))
                            }
                            AdaptiveLegendsPane(
                                viewModel = legendsViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}
