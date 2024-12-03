package com.nickoehler.brawlhalla.ranking.presentation.models

import android.content.Context
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.ranking.domain.Region
import com.nickoehler.brawlhalla.ranking.presentation.Localizable

data class RegionUi(
    val value: Region,
    val flag: String
) : Localizable {
    override fun toString(context: Context): String {
        return "${this.flag} · ${
            when (this.value) {
                Region.ALL -> context.getString(
                    R.string.region_all
                )

                Region.UNKNOWN -> context.getString(
                    R.string.region_unknown
                )

                Region.US_E -> "US-E"
                Region.EU -> "EU"
                Region.SEA -> "SEA"
                Region.BRZ -> "BRZ"
                Region.AUS -> "AUS"
                Region.US_W -> "US-W"
                Region.JPN -> "JPN"
                Region.SA -> "SA"
                Region.ME -> "ME"
            }
        }"
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

