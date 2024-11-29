package com.nickoehler.brawlhalla.search.presentation.models

import com.nickoehler.brawlhalla.search.domain.Region

data class RegionUi(
    val name: Region
)

fun Region.toRegionUi(): RegionUi {
    return RegionUi(
        name = this
    )
}