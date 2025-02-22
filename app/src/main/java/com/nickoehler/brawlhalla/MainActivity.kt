package com.nickoehler.brawlhalla

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nickoehler.brawlhalla.clans.presentation.ClanAction
import com.nickoehler.brawlhalla.clans.presentation.ClanViewModel
import com.nickoehler.brawlhalla.clans.presentation.screens.ClanDetailScreen
import com.nickoehler.brawlhalla.favorites.FavoriteAction
import com.nickoehler.brawlhalla.favorites.presentation.FavoritesViewModel
import com.nickoehler.brawlhalla.favorites.presentation.screens.FavoritesScreen
import com.nickoehler.brawlhalla.info.presentation.InfoViewModel
import com.nickoehler.brawlhalla.info.presentation.screens.InfoScreen
import com.nickoehler.brawlhalla.info.presentation.screens.LicensesScreen
import com.nickoehler.brawlhalla.legends.presentation.LegendsViewModel
import com.nickoehler.brawlhalla.legends.presentation.screens.AdaptiveLegendsPane
import com.nickoehler.brawlhalla.ranking.presentation.StatDetailViewModel
import com.nickoehler.brawlhalla.ranking.presentation.screens.AdaptiveRankingPane
import com.nickoehler.brawlhalla.ranking.presentation.screens.StatDetailScreen
import com.nickoehler.brawlhalla.ui.Route
import com.nickoehler.brawlhalla.ui.Screens
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinContext
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        println(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {

            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val configuration = LocalConfiguration.current
            val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
            val showBottomBar = Screens
                .entries.any { screen ->
                    currentDestination?.hierarchy?.any {
                        it.hasRoute(route = screen.route::class)
                    } ?: false
                }

            val layoutType = when (showBottomBar) {
                true -> if (isPortrait) NavigationSuiteType.NavigationBar else NavigationSuiteType.NavigationRail
                false -> NavigationSuiteType.None
            }

            KoinContext {
                BrawlhallaTheme {
                    NavigationSuiteScaffold(
                        modifier = Modifier
                            .imePadding()
                            .animateContentSize(),
                        layoutType = layoutType,
                        navigationSuiteItems = {
                            Screens.entries.forEach { currentScreen ->
                                val isSelected =
                                    currentDestination?.hierarchy?.any { it.hasRoute(currentScreen.route::class) } == true
                                item(
                                    selected = isSelected,
                                    onClick = {
                                        navController.navigate(currentScreen.route) {
                                            popUpTo(navController.graph.startDestinationId) {
                                                saveState = true
                                            }
                                            restoreState = true
                                            launchSingleTop = true
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

                        val playerId = intent.extras?.getInt("OPEN_STAT")
                        val clanId = intent.extras?.getInt("OPEN_CLAN")

                        LaunchedEffect(playerId) {
                            if (playerId != null && playerId != 0) {
                                navController.navigate(Route.Stat(playerId)) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = true
                                    }
                                }
                            }
                        }

                        LaunchedEffect(clanId) {
                            if (clanId != null && clanId != 0) {
                                navController.navigate(Route.Clan(clanId)) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = true
                                    }
                                }
                            }
                        }

                        NavHost(
                            modifier = Modifier
                                .safeDrawingPadding()
                                .animateContentSize()
                                .padding(horizontal = 8.dp),
                            navController = navController,
                            startDestination = Route.Favorites,
                        ) {
                            composable<Route.Stat> {

                                val player = it.toRoute<Route.Stat>()
                                val statDetailViewModel = koinViewModel<StatDetailViewModel>(
                                    parameters = { parametersOf(player.playerId) }
                                )
                                val state by statDetailViewModel.state.collectAsStateWithLifecycle()

                                StatDetailScreen(
                                    state,
                                    onStatDetailAction = statDetailViewModel::onStatDetailAction,
                                    onBack = {
                                        if (!navController.popBackStack()) {
                                            finish()
                                        }
                                    },
                                    events = statDetailViewModel.uiEvents,
                                    onPlayerSelection = { brawlhallaId ->
                                        navController.navigate(Route.Stat(brawlhallaId))
                                    },
                                    onClanSelection = { clanId ->
                                        navController.navigate(Route.Clan(clanId))
                                    }
                                )
                            }
                            composable<Route.Rankings> {
                                AdaptiveRankingPane(
                                    onClanSelection = { clanId ->
                                        navController.navigate(
                                            Route.Clan(clanId)
                                        )
                                    },
                                    onLegendSelection = { legendId ->

                                        navController.navigate(
                                            Route.Legend(legendId)
                                        )
                                    },
                                    onPlayerSelection = { brawlhallaId ->
                                        navController.navigate(
                                            Route.Stat(brawlhallaId)
                                        )
                                    }
                                )
                            }
                            composable<Route.Legends> {
                                val legendsViewmodel = koinViewModel<LegendsViewModel>(
                                    parameters = { parametersOf(0) }
                                )

                                AdaptiveLegendsPane(
                                    viewModel = legendsViewmodel,
                                )
                            }

                            composable<Route.Legend> {
                                val legend = it.toRoute<Route.Legend>()
                                val legendsViewmodel = koinViewModel<LegendsViewModel>(
                                    parameters = { parametersOf(legend.id) }
                                )

                                AdaptiveLegendsPane(
                                    viewModel = legendsViewmodel,
                                )
                            }

                            composable<Route.Favorites> {
                                val favoritesViewModel = koinViewModel<FavoritesViewModel>()
                                val favoritesState by favoritesViewModel.state.collectAsStateWithLifecycle()

                                FavoritesScreen(
                                    state = favoritesState,
                                    onFavoriteAction = { action ->
                                        favoritesViewModel.onFavoriteAction(action)
                                        when (action) {
                                            is FavoriteAction.SelectClan -> navController.navigate(
                                                Route.Clan(clanId = action.clanId)
                                            )

                                            is FavoriteAction.SelectPlayer -> navController.navigate(
                                                Route.Stat(playerId = action.brawlhallaId)
                                            )

                                            else -> {}
                                        }
                                    },
                                    onInfoSelection = {
                                        navController.navigate(Route.Info)
                                    }
                                )
                            }
                            composable<Route.Clan> {
                                val clan = it.toRoute<Route.Clan>()
                                val clanViewModel =
                                    koinViewModel<ClanViewModel>(parameters = { parametersOf(clan.clanId) })
                                ClanDetailScreen(
                                    clanViewModel.state.collectAsStateWithLifecycle().value,
                                    onClanAction = { action ->
                                        clanViewModel.onClanAction(action)
                                        if (action is ClanAction.SelectMember) {
                                            navController.navigate(
                                                Route.Stat(action.memberId)
                                            )
                                        }
                                    },
                                    events = clanViewModel.uiEvents,
                                    onBack = {
                                        if (!navController.popBackStack()) {
                                            finish()
                                        }
                                    }
                                )
                            }

                            composable<Route.Info> {
                                val infoViewModel = koinViewModel<InfoViewModel>()
                                InfoScreen(
                                    onInfoAction = infoViewModel::onInfoAction,
                                    onBack = {
                                        navController.popBackStack()
                                    },
                                    onLicensesPressed = {
                                        navController.navigate(Route.Licenses)
                                    },
                                    events = infoViewModel.events
                                )
                            }

                            composable<Route.Licenses> {
                                LicensesScreen(
                                    onBack = {
                                        navController.popBackStack()
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}