package com.nickoehler.brawlhalla.ranking.data.mappers

import com.nickoehler.brawlhalla.ranking.domain.Bracket

fun Bracket.toUrlString(): String {
    return when (this) {
        Bracket.ONE_VS_ONE -> "1v1"
        Bracket.TWO_VS_TWO -> "2v2"
        Bracket.ROTATING -> "rotating"
    }
}