package com.nickoehler.brawlhalla.ranking.domain

import com.nickoehler.brawlhalla.clans.domain.ClanDetail
import com.nickoehler.brawlhalla.core.domain.util.NetworkError
import com.nickoehler.brawlhalla.core.domain.util.Result

interface RankingsDataSource {
    suspend fun getRankings(
        bracket: Bracket,
        regions: Region,
        page: Int,
        name: String?
    ): Result<List<Ranking>, NetworkError>

    suspend fun getStat(brawlhallaId: Int): Result<StatDetail, NetworkError>

    suspend fun getRanked(brawlhallaId: Int): Result<RankingDetail, NetworkError>
    
}