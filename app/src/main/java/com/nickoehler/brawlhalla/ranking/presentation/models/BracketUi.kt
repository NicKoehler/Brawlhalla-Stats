package com.nickoehler.brawlhalla.ranking.presentation.models

import android.content.Context
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.ranking.domain.Bracket
import com.nickoehler.brawlhalla.ranking.presentation.Localizable

data class BracketUi(
    val value: Bracket,
    val emoji: String,
) : Localizable {
    override fun toString(context: Context): String {
        return "${this.emoji} · ${
            when (this.value) {
                Bracket.ONE_VS_ONE -> "1v1"
                Bracket.TWO_VS_TWO -> "2v2"
                Bracket.ROTATING -> context.getString(R.string.seasonal)
            }
        }"
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


