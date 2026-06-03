package com.nickoehler.brawlhalla.guilds.presentation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.ui.graphics.vector.ImageVector
import com.nickoehler.brawlhalla.R


enum class GuildSortType {
    JoinDate,
    Alpha,
    Rank,
    Xp,
}

fun GuildSortType.toStringResource(): Int {
    return when (this) {
        GuildSortType.JoinDate -> R.string.joinDate
        GuildSortType.Alpha -> R.string.alphabetical
        GuildSortType.Rank -> R.string.rank
        GuildSortType.Xp -> R.string.xp
    }
}

fun GuildSortType.toIcon(): ImageVector {
    return when (this) {
        GuildSortType.JoinDate -> Icons.Default.DateRange
        GuildSortType.Alpha -> Icons.Default.SortByAlpha
        GuildSortType.Rank -> Icons.Default.Flag
        GuildSortType.Xp -> Icons.Default.Numbers
    }
}