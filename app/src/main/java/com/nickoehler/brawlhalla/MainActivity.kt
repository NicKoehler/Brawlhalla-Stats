package com.nickoehler.brawlhalla

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nickoehler.brawlhalla.core.presentation.util.toNavString
import com.nickoehler.brawlhalla.legends.presentation.screens.AdaptiveLegendsPane
import com.nickoehler.brawlhalla.legends.presentation.LegendAction
import com.nickoehler.brawlhalla.legends.presentation.LegendsViewModel
import com.nickoehler.brawlhalla.ui.Route
import com.nickoehler.brawlhalla.ui.Screens
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel by viewModel<LegendsViewModel>()
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination

            enableEdgeToEdge()
            BrawlhallaTheme {
                NavigationSuiteScaffold(
                    modifier = Modifier.imePadding(),
                    navigationSuiteItems = {
                        Screens.entries.forEach { currentScreen ->
                            if (currentRoute != null) {
                                item(
                                    selected = currentRoute.toNavString() == currentScreen.route.toString(),
                                    onClick = {
                                        if (currentRoute.toNavString() != currentScreen.route.toString()) {
                                            navController.navigate(currentScreen.route) {
                                                popUpTo(navController.graph.startDestinationId)
                                                launchSingleTop = true
                                            }
                                        }
                                    },
                                    icon = {
                                        Icon(currentScreen.icon, currentScreen.title)
                                    },
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
                        startDestination = Route.Home,
                    ) {
                        composable<Route.Home> {
                            AdaptiveLegendsPane(
                                viewModel = viewModel,
                            )
                        }
                        composable<Route.Legend> {
                            val args = it.toRoute<Route.Legend>()
                            viewModel.onLegendAction(LegendAction.SelectLegend(legendId = args.id))
                            AdaptiveLegendsPane(
                                viewModel = viewModel
                            )
                        }
                        composable<Route.Search> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Search Screen")
                            }
                        }
                    }
                }
            }
        }
    }
}
