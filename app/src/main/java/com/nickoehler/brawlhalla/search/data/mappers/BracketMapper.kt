package com.nickoehler.brawlhalla.search.data.mappers

import com.nickoehler.brawlhalla.search.domain.Bracket

fun Bracket.toUrlString(): String {
    return when (this) {
        Bracket.ONE_VS_ONE -> "1v1"
        Bracket.TWO_VS_TWO -> "2v2"
        Bracket.ROTATING -> "rotating"
    }
}