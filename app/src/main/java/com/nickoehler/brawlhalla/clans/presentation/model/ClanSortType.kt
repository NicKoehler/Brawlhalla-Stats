package com.nickoehler.brawlhalla.clans.presentation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.ui.graphics.vector.ImageVector
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

fun ClanSortType.toIcon(): ImageVector {
    return when (this) {
        ClanSortType.JoinDate -> Icons.Default.DateRange
        ClanSortType.Alpha -> Icons.Default.SortByAlpha
        ClanSortType.Rank -> Icons.Default.Flag
        ClanSortType.Xp -> Icons.Default.Numbers
    }
}