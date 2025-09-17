package com.nickoehler.brawlhalla

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.adaptive.navigationsuite.rememberNavigationSuiteScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.SceneStrategy
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.nickoehler.brawlhalla.clans.presentation.ClanAction
import com.nickoehler.brawlhalla.clans.presentation.ClanViewModel
import com.nickoehler.brawlhalla.clans.presentation.screens.ClanDetailScreen
import com.nickoehler.brawlhalla.core.presentation.ThemeViewModel
import com.nickoehler.brawlhalla.core.presentation.WeaponAction
import com.nickoehler.brawlhalla.favorites.FavoriteAction
import com.nickoehler.brawlhalla.favorites.presentation.FavoritesViewModel
import com.nickoehler.brawlhalla.favorites.presentation.screens.FavoritesScreen
import com.nickoehler.brawlhalla.legends.presentation.LegendAction
import com.nickoehler.brawlhalla.legends.presentation.LegendDetailAction
import com.nickoehler.brawlhalla.legends.presentation.LegendDetailViewModel
import com.nickoehler.brawlhalla.legends.presentation.LegendsViewModel
import com.nickoehler.brawlhalla.legends.presentation.screens.LegendDetailScreen
import com.nickoehler.brawlhalla.legends.presentation.screens.LegendListScreen
import com.nickoehler.brawlhalla.ranking.presentation.RankingAction
import com.nickoehler.brawlhalla.ranking.presentation.RankingViewModel
import com.nickoehler.brawlhalla.ranking.presentation.StatDetailViewModel
import com.nickoehler.brawlhalla.ranking.presentation.screens.RankingListScreen
import com.nickoehler.brawlhalla.ranking.presentation.screens.StatDetailScreen
import com.nickoehler.brawlhalla.settings.presentation.SettingsViewModel
import com.nickoehler.brawlhalla.settings.presentation.screens.LicensesScreen
import com.nickoehler.brawlhalla.settings.presentation.screens.SettingsScreen
import com.nickoehler.brawlhalla.ui.Route
import com.nickoehler.brawlhalla.ui.Screens
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
    }

    @OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {

            val backStack = rememberNavBackStack<Route>(Route.Favorites)
            val configuration = LocalConfiguration.current
            val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
            val layoutType by remember {
                derivedStateOf {
                    if (isPortrait) NavigationSuiteType.NavigationBar else NavigationSuiteType.NavigationRail
                }
            }

            val navTransition = NavDisplay.transitionSpec {
                EnterTransition.None.togetherWith(ExitTransition.None)
            } + NavDisplay.popTransitionSpec {
                EnterTransition.None.togetherWith(ExitTransition.None)
            } + NavDisplay.predictivePopTransitionSpec {
                EnterTransition.None.togetherWith(ExitTransition.None)
            }

            val themeViewModel = koinViewModel<ThemeViewModel>()
            val theme by themeViewModel.theme.collectAsStateWithLifecycle()
            val navigatorScaffoldState = rememberNavigationSuiteScaffoldState()
            val snackBarHostState = remember { SnackbarHostState() }

            LaunchedEffect(backStack.last(), isPortrait) {
                if (!isPortrait || Screens.entries.map { it.route }.contains(backStack.last())) {
                    navigatorScaffoldState.show()
                } else {
                    navigatorScaffoldState.hide()
                }
            }

            BrawlhallaTheme(theme) {
                NavigationSuiteScaffold(
                    state = navigatorScaffoldState,
                    modifier = Modifier
                        .imePadding()
                        .animateContentSize(),
                    layoutType = layoutType,
                    navigationSuiteItems = {
                        Screens.entries.forEach { currentScreen ->
                            val isSelected = backStack.last() == currentScreen.route
                            item(
                                selected = isSelected,
                                onClick = {
                                    if (isSelected) return@item
                                    if (backStack.last() != currentScreen.route) {
                                        backStack.add(currentScreen.route)
                                    }
                                },
                                icon = {
                                    AnimatedContent(
                                        isSelected,
                                        transitionSpec = {
                                            fadeIn().togetherWith(
                                                fadeOut()
                                            )
                                        }
                                    ) {
                                        if (it) {
                                            Icon(
                                                currentScreen.selectedIcon,
                                                stringResource(currentScreen.title)
                                            )
                                        } else {
                                            Icon(
                                                currentScreen.unselectedIcon,
                                                stringResource(currentScreen.title)
                                            )
                                        }
                                    }
                                },
                                label = {
                                    Text(stringResource(currentScreen.title))
                                }
                            )
                        }
                    }
                ) {

                    val playerId = intent.extras?.getLong("OPEN_STAT")
                    val clanId = intent.extras?.getLong("OPEN_CLAN")

                    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
                    val directive = remember(windowAdaptiveInfo) {
                        calculatePaneScaffoldDirective(windowAdaptiveInfo)
                            .copy(horizontalPartitionSpacerSize = 0.dp)
                    }

                    val legendsViewModel = koinViewModel<LegendsViewModel>()

                    val listDetailStrategy: SceneStrategy<NavKey> =
                        rememberListDetailSceneStrategy(directive = directive)

                    LaunchedEffect(playerId) {
                        if (playerId != null && playerId != 0L) {
                            backStack.clear()
                            backStack.add(Route.Stat(playerId))
                        }
                    }

                    LaunchedEffect(clanId) {
                        if (clanId != null && clanId != 0L) {
                            backStack.clear()
                            backStack.add(Route.Clan(clanId))
                        }
                    }

                    NavDisplay(
                        modifier = Modifier
                            .safeDrawingPadding()
                            .animateContentSize()
                            .padding(horizontal = 16.dp),
                        backStack = backStack,
                        onBack = { backStack.removeLastOrNull() },
                        entryDecorators = listOf(
                            rememberSavedStateNavEntryDecorator(),
                            rememberViewModelStoreNavEntryDecorator(),
                            rememberSceneSetupNavEntryDecorator()
                        ),
                        transitionSpec = {
                            slideInHorizontally(initialOffsetX = { it }) togetherWith
                                    slideOutHorizontally(targetOffsetX = { -it })
                        },
                        popTransitionSpec = {
                            slideInHorizontally(initialOffsetX = { -it }) togetherWith
                                    slideOutHorizontally(targetOffsetX = { it })
                        },
                        predictivePopTransitionSpec = {
                            slideInHorizontally(initialOffsetX = { -it }) togetherWith
                                    slideOutHorizontally(targetOffsetX = { it })
                        },
                        sceneStrategy = listDetailStrategy,
                        entryProvider = entryProvider {
                            entry<Route.Clan> {
                                val clanViewModel =
                                    koinViewModel<ClanViewModel>(parameters = {
                                        parametersOf(
                                            it.clanId
                                        )
                                    })
                                val clanState by clanViewModel.state.collectAsStateWithLifecycle()

                                ClanDetailScreen(
                                    clanState,
                                    onClanAction = { action ->
                                        clanViewModel.onClanAction(action)
                                        if (action is ClanAction.SelectMember) {
                                            backStack.add(
                                                Route.Stat(action.memberId)
                                            )
                                        }
                                    },
                                    events = clanViewModel.uiEvents,
                                    onBack = {
                                        backStack.removeLastOrNull()
                                    }
                                )
                            }

                            entry<Route.Favorites>(metadata = navTransition) {
                                val favoritesViewModel = koinViewModel<FavoritesViewModel>()
                                val favoritesState by favoritesViewModel.state.collectAsStateWithLifecycle()

                                FavoritesScreen(
                                    state = favoritesState,
                                    snackBarHostState = snackBarHostState,
                                    onFavoriteAction = { action ->
                                        favoritesViewModel.onFavoriteAction(action)
                                        when (action) {
                                            is FavoriteAction.ClanClicked -> backStack.add(
                                                Route.Clan(clanId = action.clanId)
                                            )

                                            is FavoriteAction.PlayerClicked -> backStack.add(
                                                Route.Stat(playerId = action.brawlhallaId)
                                            )

                                            else -> {}
                                        }
                                    },
                                    onInfoSelection = {
                                        backStack.add(Route.Info)
                                    }
                                )
                            }

                            entry<Route.Info> {
                                val settingsViewModel = koinViewModel<SettingsViewModel>()
                                val state by settingsViewModel.state.collectAsStateWithLifecycle()
                                SettingsScreen(
                                    settingsState = state,
                                    onSettingsAction = settingsViewModel::onSettingsAction,
                                    onBack = {
                                        backStack.removeLastOrNull()
                                    },
                                    onLicensesPressed = {
                                        backStack.add(Route.Licenses)
                                    },
                                    events = settingsViewModel.events
                                )
                            }

                            entry<Route.Legend>(
                                metadata = ListDetailSceneStrategy.detailPane()
                            ) {
                                val viewModel =
                                    koinViewModel<LegendDetailViewModel> { parametersOf(it.id) }
                                val state by viewModel.state.collectAsStateWithLifecycle()
                                LegendDetailScreen(
                                    state = state,
                                    onLegendAction = { action ->
                                        if (action is LegendDetailAction.SelectStat) {
                                            legendsViewModel.onLegendAction(
                                                LegendAction.SelectStat(
                                                    action.stat,
                                                    action.value
                                                )
                                            )
                                            if (isPortrait) {
                                                backStack.removeLastOrNull()
                                            }
                                        }
                                    },
                                    onWeaponAction = { action ->
                                        if (action is WeaponAction.Click) {
                                            legendsViewModel.onWeaponAction(action)
                                            if (isPortrait) {
                                                backStack.removeLastOrNull()
                                            }
                                        }
                                    }
                                )
                            }

                            entry<Route.Legends>(
                                metadata = navTransition + ListDetailSceneStrategy.listPane(),
                            ) {
                                val state by legendsViewModel.state.collectAsStateWithLifecycle()

                                LegendListScreen(
                                    state = state,
                                    events = legendsViewModel.uiEvents,
                                    onLegendAction = { action ->
                                        legendsViewModel.onLegendAction(action)
                                        if (action is LegendAction.SelectLegend) {
                                            backStack.add(
                                                Route.Legend(action.legendId)
                                            )
                                        }
                                    },
                                    onWeaponAction = legendsViewModel::onWeaponAction,
                                )
                            }

                            entry<Route.Licenses> {
                                LicensesScreen(
                                    onBack = {
                                        backStack.removeLastOrNull()
                                    },
                                )
                            }

                            entry<Route.Rankings>(
                                metadata = navTransition + ListDetailSceneStrategy.listPane()
                            ) {

                                val viewModel = koinViewModel<RankingViewModel>()
                                val rankingState by viewModel.state.collectAsStateWithLifecycle()

                                RankingListScreen(
                                    state = rankingState,
                                    events = viewModel.uiEvents,
                                    onRankingAction = { action ->
                                        viewModel.onRankingAction(action)
                                        when (action) {
                                            is RankingAction.SelectRanking -> {
                                                backStack.add(Route.Stat(action.brawlhallaId))
                                            }

                                            is RankingAction.Search -> {
                                                try {
                                                    if (action.query.all { it.isDigit() }) {
                                                        backStack.add(Route.Stat(action.query.toLong()))
                                                    }
                                                } catch (_: Exception) {
                                                    viewModel.onRankingAction(
                                                        RankingAction.Search(
                                                            action.query, force = true
                                                        )
                                                    )
                                                }
                                            }

                                            else -> Unit
                                        }
                                    }
                                )
                            }

                            entry<Route.Stat>(
                                metadata = ListDetailSceneStrategy.detailPane()
                            ) {
                                val statDetailViewModel =
                                    koinViewModel<StatDetailViewModel>(
                                        parameters = { parametersOf(it.playerId) }
                                    )
                                val state by statDetailViewModel.state.collectAsStateWithLifecycle()

                                StatDetailScreen(
                                    state,
                                    onStatDetailAction = statDetailViewModel::onStatDetailAction,
                                    onBack = {
                                        backStack.removeLastOrNull()
                                    },
                                    events = statDetailViewModel.uiEvents,
                                    onPlayerSelection = { brawlhallaId ->
                                        backStack.add(Route.Stat(brawlhallaId))
                                    },
                                    onClanSelection = { clanId ->
                                        backStack.add(Route.Clan(clanId))
                                    }
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}