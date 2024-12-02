package com.nickoehler.brawlhalla.ranking.presentation.models

import com.nickoehler.brawlhalla.ranking.domain.Bracket

data class BracketUi(
    val value: Bracket,
    val emoji: String,
) {
    override fun toString(): String {
        return this.emoji
    }
}

fun Bracket.toBracketUi(): BracketUi {
    return BracketUi(
        value = this,
        when (this) {
            Bracket.ONE_VS_ONE -> "1\uFE0F⃣"
            Bracket.TWO_VS_TWO -> "2\uFE0F⃣"
            Bracket.ROTATING -> "\uD83C\uDF00"
        }
    )
}


