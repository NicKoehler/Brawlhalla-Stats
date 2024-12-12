package com.nickoehler.brawlhalla

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nickoehler.brawlhalla.clans.presentation.ClanAction
import com.nickoehler.brawlhalla.clans.presentation.ClanViewModel
import com.nickoehler.brawlhalla.clans.presentation.screens.ClanDetailScreen
import com.nickoehler.brawlhalla.core.presentation.UiEvent
import com.nickoehler.brawlhalla.core.presentation.util.toString
import com.nickoehler.brawlhalla.favorites.FavoriteAction
import com.nickoehler.brawlhalla.favorites.presentation.FavoritesViewModel
import com.nickoehler.brawlhalla.favorites.presentation.screens.FavoritesScreen
import com.nickoehler.brawlhalla.info.presentation.InfoViewModel
import com.nickoehler.brawlhalla.info.presentation.model.InfoAction
import com.nickoehler.brawlhalla.info.presentation.screens.InfoScreen
import com.nickoehler.brawlhalla.legends.presentation.screens.AdaptiveLegendsPane
import com.nickoehler.brawlhalla.ranking.presentation.screens.AdaptiveRankingPane
import com.nickoehler.brawlhalla.ranking.presentation.util.toString
import com.nickoehler.brawlhalla.ui.Route
import com.nickoehler.brawlhalla.ui.Screens
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import com.plcoding.cryptotracker.core.presentation.util.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val navController = rememberNavController()
            val navBackStackEntry = navController.currentBackStackEntryAsState().value
            val currentDestination = navBackStackEntry?.destination

            val showBottomBar = Screens
                .entries.any { screen ->
                    currentDestination?.hierarchy?.any {
                        it.hasRoute(route = screen.route::class)
                    } ?: false
                }

            BrawlhallaTheme {
                NavigationSuiteScaffold(
                    modifier = Modifier
                        .imePadding()
                        .animateContentSize(),
                    layoutType = if (showBottomBar) NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(
                        currentWindowAdaptiveInfo()
                    ) else NavigationSuiteType.None,

                    navigationSuiteItems = {
                        Screens.entries.forEach { currentScreen ->
                            val isSelected = currentDestination?.hierarchy?.any {
                                it.hasRoute(route = currentScreen.route::class)
                            } ?: false
                            item(
                                selected = isSelected,
                                onClick = {
                                    if (!isSelected)
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
                                        stringResource(currentScreen.title),
                                    )
                                },
                                label = {
                                    if (isSelected) {
                                        Text(stringResource(currentScreen.title))
                                    }
                                }
                            )
                        }

                    }
                ) {
                    NavHost(
                        modifier = Modifier
                            .safeDrawingPadding()
                            .animateContentSize()
                            .padding(horizontal = 8.dp),
                        navController = navController,
                        startDestination = Route.Favorites,
                    ) {
                        composable<Route.Ranking> {
                            val ranking = it.toRoute<Route.Ranking>()
                            AdaptiveRankingPane(
                                ranking.playerId,
                                onClanSelection = { clanId ->
                                    navController.navigate(Route.Clan(clanId)) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                    }
                                },
                            )
                        }
                        composable<Route.Legend> {
                            val legend = it.toRoute<Route.Legend>()
                            AdaptiveLegendsPane(
                                legend.id,
                            )
                        }
                        composable<Route.Favorites> {
                            val favoritesViewModel = koinViewModel<FavoritesViewModel>()
                            FavoritesScreen(
                                state = favoritesViewModel.state.collectAsStateWithLifecycle().value,
                                onFavoriteAction = { action ->
                                    favoritesViewModel.onFavoriteAction(action)
                                    when (action) {
                                        is FavoriteAction.SelectClan -> navController.navigate(
                                            Route.Clan(clanId = action.clanId)
                                        ) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                        }

                                        is FavoriteAction.SelectPlayer -> navController.navigate(
                                            Route.Ranking(playerId = action.brawlhallaId)
                                        ) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                        }

                                        else -> {}
                                    }
                                },
                                onInfoSelection = {
                                    navController.navigate(Route.Info)
                                }
                            )
                        }
                        composable<Route.Clan> {
                            val clanViewModel = koinViewModel<ClanViewModel>()
                            val clan = it.toRoute<Route.Clan>()

                            ObserveAsEvents(clanViewModel.uiEvents) { event ->
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

                                    else -> {}
                                }
                            }

                            ClanDetailScreen(
                                clan.clanId,
                                clanViewModel.state.collectAsStateWithLifecycle().value,
                                onClanAction = { action ->
                                    clanViewModel.onClanAction(action)
                                    if (action is ClanAction.SelectMember) {
                                        navController.navigate(Route.Ranking(action.memberId)) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                        }
                                    }
                                }
                            )
                        }
                        composable<Route.Info> {
                            val infoViewModel = InfoViewModel()
                            InfoScreen(
                                { action ->
                                    infoViewModel.onInfoAction(action)
                                    if (action is InfoAction.GoBack) {
                                        navController.popBackStack()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}