package com.nickoehler.brawlhalla.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

enum class Screens(
    val title: String,
    val route: Route,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    LEGEND(
        title = "Legends",
        route = Route.Legend(),
        selectedIcon = Icons.Default.People,
        unselectedIcon = Icons.Outlined.People
    ),
    SEARCH(
        title = "Search",
        route = Route.Search,
        selectedIcon = Icons.Default.Search,
        unselectedIcon = Icons.Outlined.Search
    );
}


sealed interface Route {

    @Serializable
    data object Search : Route


    @Serializable
    data class Legend(val id: Int? = null) : Route
}


