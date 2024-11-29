package com.nickoehler.brawlhalla.search.domain

import com.nickoehler.brawlhalla.core.domain.util.NetworkError
import com.nickoehler.brawlhalla.core.domain.util.Result

interface RankingsDataSource {
    suspend fun getRankings(
        bracket: Bracket,
        regions: Region,
        page: Int,
        name: String?
    ): Result<List<Ranking>, NetworkError>
}