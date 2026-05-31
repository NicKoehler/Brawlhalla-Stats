package com.nickoehler.brawlhalla.ranking.data.mappers

import com.nickoehler.brawlhalla.ranking.domain.Region

fun Region.toUrlString(): String {
    return when (this) {
        Region.ALL -> "ALL"
        Region.US_E -> "US_E"
        Region.EU -> "EU"
        Region.SEA -> "SEA"
        Region.BRZ -> "BRZ"
        Region.AUS -> "AUS"
        Region.US_W -> "US_W"
        Region.JPN -> "JPN"
        Region.SA -> "SA"
        Region.ME -> "ME"
        Region.UNKNOWN -> "?"
    }
}

fun String.toRegion(): Region {
    return when (this) {
        "US-E" -> Region.US_E
        "EU" -> Region.EU
        "SEA" -> Region.SEA
        "BRZ" -> Region.BRZ
        "AUS" -> Region.AUS
        "US-W" -> Region.US_W
        "JPN" -> Region.JPN
        "SA" -> Region.SA
        "ME" -> Region.ME
        else -> Region.UNKNOWN
    }
}

fun Int.toRegion(): Region {
    return Region.entries.first { it.num == this }
}