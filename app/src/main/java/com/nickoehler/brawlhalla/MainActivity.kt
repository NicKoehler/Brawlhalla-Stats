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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nickoehler.brawlhalla.favorites.presentation.screens.FavoritesScreen
import com.nickoehler.brawlhalla.legends.presentation.screens.AdaptiveLegendsPane
import com.nickoehler.brawlhalla.ranking.presentation.screens.AdaptiveRankingPane
import com.nickoehler.brawlhalla.ui.Route
import com.nickoehler.brawlhalla.ui.Screens
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            val startRoute = Route.Favorites
            val navController = rememberNavController()
            val navBackStackEntry = navController.currentBackStackEntryAsState().value
            val currentDestination = navBackStackEntry?.destination


            BrawlhallaTheme {
                NavigationSuiteScaffold(
                    modifier = Modifier.imePadding(),
                    navigationSuiteItems = {
                        Screens.entries.forEach { currentScreen ->
                            val isSelected = currentDestination?.hierarchy?.any {
                                it.hasRoute(route = currentScreen.route::class)
                            } ?: false
                            item(
                                selected = isSelected,
                                onClick = {
                                    navController.navigate(currentScreen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
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
                ) {
                    NavHost(
                        modifier = Modifier
                            .safeDrawingPadding()
                            .padding(horizontal = 8.dp),
                        navController = navController,
                        startDestination = startRoute,
                    ) {
                        composable<Route.Ranking> {
                            val ranking = it.toRoute<Route.Ranking>()
                            AdaptiveRankingPane(
                                ranking.playerId,
                                ranking.clanId
                            )
                        }
                        composable<Route.Legend> {
                            val legend = it.toRoute<Route.Legend>()
                            AdaptiveLegendsPane(
                                legend.id,
                            )
                        }
                        composable<Route.Favorites> {
                            FavoritesScreen()
                        }
                    }
                }
            }
        }
    }
}
