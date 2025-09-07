package com.nickoehler.brawlhalla.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Leaderboard
import androidx.compose.material.icons.outlined.People
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.nickoehler.brawlhalla.R
import kotlinx.serialization.Serializable

enum class Screens(
    val title: Int,
    val route: Route,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    LEGENDS(
        title = R.string.legends,
        route = Route.Legends,
        selectedIcon = Icons.Default.People,
        unselectedIcon = Icons.Outlined.People
    ),
    FAVORITES(
        R.string.favorites,
        route = Route.Favorites,
        selectedIcon = Icons.Default.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    RANKINGS(
        title = R.string.rankings,
        route = Route.Rankings,
        selectedIcon = Icons.Default.Leaderboard,
        unselectedIcon = Icons.Outlined.Leaderboard
    );
}


sealed interface Route : NavKey {

    @Serializable
    data object Info : Route

    @Serializable
    data object Licenses : Route

    @Serializable
    data object Favorites : Route

    @Serializable
    data object Legends : Route

    @Serializable
    data class Legend(val id: Long) : Route

    @Serializable
    data class Clan(val clanId: Long) : Route

    @Serializable
    data object Rankings : Route

    @Serializable
    data class Stat(val playerId: Long) : Route
}


