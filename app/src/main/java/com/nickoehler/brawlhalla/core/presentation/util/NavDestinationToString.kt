package com.nickoehler.brawlhalla.core.presentation.util

import androidx.navigation.NavDestination

fun NavDestination.toNavString(): String {
    return this.route?.split(".")?.last().toString()
}