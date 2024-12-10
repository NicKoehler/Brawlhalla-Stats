package com.nickoehler.brawlhalla.ranking.presentation.util

import android.content.Context
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.ranking.domain.RankingMessage

fun RankingMessage.toString(context: Context): String {
    return when (this) {
        is RankingMessage.Removed -> this.name + " " + context.getString(R.string.prefRemovedFavorites)
        is RankingMessage.Saved -> this.name + " " + context.getString(R.string.prefAddedFavorites)
    }
}