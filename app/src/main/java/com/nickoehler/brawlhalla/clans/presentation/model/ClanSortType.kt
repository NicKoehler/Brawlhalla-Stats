package com.nickoehler.brawlhalla.clans.presentation.model

import com.nickoehler.brawlhalla.R


enum class ClanSortType {
    JoinDate,
    Alpha,
    Rank,
    Xp,
}

fun ClanSortType.toStringResource(): Int {
    return when (this) {
        ClanSortType.JoinDate -> R.string.joinDate
        ClanSortType.Alpha -> R.string.alphabetical
        ClanSortType.Rank -> R.string.rank
        ClanSortType.Xp -> R.string.xp
    }
}