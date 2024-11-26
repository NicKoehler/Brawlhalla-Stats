package com.nickoehler.brawlhalla.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

enum class Screens(val title: String, val icon: ImageVector, val route: Route) {
    HOME(title = "Home", icon = Icons.Default.Home, route = Route.Home),
    SEARCH(title = "Search", icon = Icons.Default.Search, route = Route.Search);
}


sealed interface Route {

    override fun toString(): String

    @Serializable
    object Home : Route {
        override fun toString(): String = "Home"
    }

    @Serializable
    object Search : Route {
        override fun toString(): String = "Search"
    }

    @Serializable
    data class Legend(val id: Int) : Route {
        override fun toString(): String = "Legend/$id"
    }
}


