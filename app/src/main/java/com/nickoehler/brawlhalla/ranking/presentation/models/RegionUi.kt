package com.nickoehler.brawlhalla.ranking.presentation.models

import com.nickoehler.brawlhalla.ranking.domain.Region

data class RegionUi(
    val name: Region,
    val flag: String
)

fun Region.toRegionUi(): RegionUi {
    return RegionUi(
        name = this,
        flag = when (this) {
            Region.US_E -> "🇺🇸"
            Region.EU -> "🇪🇺"
            Region.SEA -> "🇨🇳"
            Region.BRZ -> "🇧🇷"
            Region.AUS -> "🇦🇺"
            Region.US_W -> "🇺🇸"
            Region.JPN -> "🇯🇵"
            Region.SA -> "🇿🇦"
            Region.ME -> "🇰🇼"
            else -> "?"
        }
    )
}

