package com.nickoehler.brawlhalla.ranking.data.mappers

import com.nickoehler.brawlhalla.ranking.domain.Region

fun Region.toUrlString(): String {
    return when (this) {
        Region.ALL -> "all"
        Region.US_E -> "us-e"
        Region.EU -> "eu"
        Region.SEA -> "sea"
        Region.BRZ -> "brz"
        Region.AUS -> "aus"
        Region.US_W -> "us-w"
        Region.JPN -> "jpn"
        Region.SA -> "sa"
        Region.ME -> "me"
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