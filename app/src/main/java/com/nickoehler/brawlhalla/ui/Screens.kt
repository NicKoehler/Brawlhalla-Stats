package com.nickoehler.brawlhalla.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

enum class Screens(val title: String, val icon: ImageVector, val route: Route) {
    LEGEND(title = "Legends", icon = Icons.Default.People, route = Route.Legend()),
    SEARCH(title = "Search", icon = Icons.Default.Search, route = Route.Search);
}


sealed interface Route {


    @Serializable
    data object Search : Route


    @Serializable
    data class Legend(val id: Int? = null) : Route
}


