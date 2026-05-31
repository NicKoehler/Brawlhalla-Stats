package com.nickoehler.brawlhalla.ranking.data.mappers

import com.nickoehler.brawlhalla.ranking.domain.GameMode

fun GameMode.toUrlString(): String {
    return when (this) {
        GameMode.ONE_VS_ONE -> "1v1"
        GameMode.TWO_VS_TWO -> "2v2"
        GameMode.THREE_VS_THREE -> "3v3"
    }
}