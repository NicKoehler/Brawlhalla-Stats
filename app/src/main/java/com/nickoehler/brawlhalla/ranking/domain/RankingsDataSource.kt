package com.nickoehler.brawlhalla.ranking.domain

import com.nickoehler.brawlhalla.core.domain.util.NetworkError
import com.nickoehler.brawlhalla.core.domain.util.Result

interface RankingsDataSource {
    suspend fun getRankings(
        bracket: Bracket,
        regions: Region,
        page: Int,
        name: String? = null
    ): Result<List<Ranking>, NetworkError>

    suspend fun getStat(brawlhallaId: Long): Result<StatDetail, NetworkError>

    suspend fun getRanked(brawlhallaId: Long): Result<RankingDetail, NetworkError>

}