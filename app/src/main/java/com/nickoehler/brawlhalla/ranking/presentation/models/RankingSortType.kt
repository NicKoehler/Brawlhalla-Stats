package com.nickoehler.brawlhalla.ranking.presentation.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material.icons.filled.StackedLineChart
import androidx.compose.material.icons.filled.Timer
import androidx.compose.ui.graphics.vector.ImageVector
import com.nickoehler.brawlhalla.R

enum class StatLegendSortType {
    Alpha,
    Level,
    Time,
    Games,
    Wins,
}

enum class GeneralRankingSortType {
    Alpha,
    PeakRating,
    Rating,
    WinRate,
    Games,
    Wins
}

sealed interface RankingSortType {
    data class StatLegend(val statLegend: StatLegendSortType) : RankingSortType
    data class RankingLegend(val rankingLegend: GeneralRankingSortType) : RankingSortType
    data class Team(val team: GeneralRankingSortType) : RankingSortType
}

fun StatLegendSortType.toStringResource(): Int {
    return when (this) {
        StatLegendSortType.Alpha -> R.string.alphabetical
        StatLegendSortType.Level -> R.string.level
        StatLegendSortType.Time -> R.string.matchTime
        StatLegendSortType.Games -> R.string.games
        StatLegendSortType.Wins -> R.string.wins
    }
}

fun StatLegendSortType.toIcon(): ImageVector {
    return when (this) {
        StatLegendSortType.Alpha -> Icons.Default.SortByAlpha
        StatLegendSortType.Level -> Icons.Default.ArrowCircleUp
        StatLegendSortType.Time -> Icons.Default.Timer
        StatLegendSortType.Games -> Icons.Default.Games
        StatLegendSortType.Wins -> Icons.Default.CheckCircle
    }
}

fun GeneralRankingSortType.toStringResource(): Int {
    return when (this) {
        GeneralRankingSortType.Alpha -> R.string.alphabetical
        GeneralRankingSortType.PeakRating -> R.string.peakRating
        GeneralRankingSortType.Rating -> R.string.rating
        GeneralRankingSortType.WinRate -> R.string.winRate
        GeneralRankingSortType.Games -> R.string.games
        GeneralRankingSortType.Wins -> R.string.wins
    }
}

fun GeneralRankingSortType.toIcon(): ImageVector {
    return when (this) {
        GeneralRankingSortType.Alpha -> Icons.Default.SortByAlpha
        GeneralRankingSortType.PeakRating -> Icons.Default.Flag
        GeneralRankingSortType.Rating -> Icons.Default.StackedLineChart
        GeneralRankingSortType.WinRate -> Icons.Default.Percent
        GeneralRankingSortType.Games -> Icons.Default.Games
        GeneralRankingSortType.Wins -> Icons.Default.CheckCircle
    }
}