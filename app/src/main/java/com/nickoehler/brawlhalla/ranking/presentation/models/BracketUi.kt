package com.nickoehler.brawlhalla.ranking.presentation.models

import android.content.Context
import com.nickoehler.brawlhalla.ranking.domain.GameMode
import com.nickoehler.brawlhalla.ranking.presentation.Localizable

data class BracketUi(
    val value: GameMode,
    val emoji: String,
) : Localizable {
    override fun toString(context: Context): String {
        return "${this.emoji} · ${
            when (this.value) {
                GameMode.ONE_VS_ONE -> "1v1"
                GameMode.TWO_VS_TWO -> "2v2"
                GameMode.THREE_VS_THREE -> "3v3"
            }
        }"
    }
}

fun GameMode.toBracketUi(): BracketUi {
    return BracketUi(
        value = this,
        when (this) {
            GameMode.ONE_VS_ONE -> "1\uFE0F⃣"
            GameMode.TWO_VS_TWO -> "2\uFE0F⃣"
            GameMode.THREE_VS_THREE -> "3\uFE0F⃣"
        }
    )
}


