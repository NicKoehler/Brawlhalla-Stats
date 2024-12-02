package com.nickoehler.brawlhalla.ranking.presentation.models

import com.nickoehler.brawlhalla.ranking.domain.Region

data class RegionUi(
    val value: Region,
    val flag: String
) {
    override fun toString(): String {
        return this.flag
    }
}

fun Region.toRegionUi(): RegionUi {
    return RegionUi(
        value = this,
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
            Region.ALL -> "🌍"
            else -> "?"
        }
    )
}

