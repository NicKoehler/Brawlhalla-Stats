package com.nickoehler.brawlhalla.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Leaderboard
import androidx.compose.material.icons.outlined.People
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

enum class Screens(
    val title: String,
    val route: Route,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    LEGENDS(
        title = "Legends",
        route = Route.Legend(),
        selectedIcon = Icons.Default.People,
        unselectedIcon = Icons.Outlined.People
    ),
    FAVORITES(
        "Favorites",
        route = Route.Favorites,
        selectedIcon = Icons.Default.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    RANKINGS(
        title = "Rankings",
        route = Route.Ranking(),
        selectedIcon = Icons.Default.Leaderboard,
        unselectedIcon = Icons.Outlined.Leaderboard
    );
}


sealed interface Route {

    @Serializable
    data class Ranking(val playerId: Int? = null, val clanId: Int? = null) : Route


    @Serializable
    data object Favorites : Route


    @Serializable
    data class Legend(val id: Int? = null) : Route
}


