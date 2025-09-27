package com.nickoehler.brawlhalla.legends.presentation

import com.nickoehler.brawlhalla.legends.domain.LegendStat

sealed interface LegendDetailAction {
    data class SelectStat(val stat: LegendStat, val value: Int) : LegendDetailAction
}
