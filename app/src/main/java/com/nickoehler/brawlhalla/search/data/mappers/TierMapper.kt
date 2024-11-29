package com.nickoehler.brawlhalla.search.data.mappers

import com.nickoehler.brawlhalla.search.domain.Tier

fun String.toTier(): Tier {
    return when (this) {
        "Valhallan" -> Tier.VALHALLAN
        "Diamond" -> Tier.DIAMOND
        "Platinum" -> Tier.PLATINUM
        "Gold" -> Tier.GOLD
        "Silver" -> Tier.SILVER
        "Bronze" -> Tier.BRONZE
        "Tin" -> Tier.TIN
        else -> Tier.UNKNOWN
    }
}