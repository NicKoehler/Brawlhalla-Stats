package com.nickoehler.brawlhalla.legends.domain

import com.nickoehler.brawlhalla.core.domain.util.NetworkError
import com.nickoehler.brawlhalla.core.domain.util.Result

interface LegendsDataSource {
    suspend fun getLegends(): Result<List<Legend>, NetworkError>
    suspend fun getLegendDetail(legendId: Long): Result<LegendDetail, NetworkError>
}