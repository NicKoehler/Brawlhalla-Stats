package com.nickoehler.brawlhalla.search.data.mappers

import com.nickoehler.brawlhalla.search.domain.Region

fun Region.toUrlString(): String {
    return when (this) {
        Region.ALL -> "all"
        Region.US_E -> "us_e"
        Region.EU -> "eu"
        Region.SEA -> "sea"
        Region.BRZ -> "brz"
        Region.AUS -> "aus"
        Region.US_W -> "us_w"
        Region.JPN -> "jpn"
        Region.SA -> "sa"
        Region.ME -> "me"
        Region.UNKNOWN -> "?"
    }
}

fun String.toRegion(): Region {
    return when (this) {
        "all" -> Region.ALL
        "us_e" -> Region.US_E
        "eu" -> Region.EU
        "sea" -> Region.SEA
        "brz" -> Region.BRZ
        "aus" -> Region.AUS
        "us_w" -> Region.US_W
        "jpn" -> Region.JPN
        "sa" -> Region.SA
        "me" -> Region.ME
        else -> Region.UNKNOWN
    }
}