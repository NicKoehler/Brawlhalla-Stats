package com.nickoehler.brawlhalla.ranking.data

import com.nickoehler.brawlhalla.core.data.networking.safeCall
import com.nickoehler.brawlhalla.core.domain.util.NetworkError
import com.nickoehler.brawlhalla.core.domain.util.Result
import com.nickoehler.brawlhalla.core.domain.util.map
import com.nickoehler.brawlhalla.ranking.data.dto.RankingsDto
import com.nickoehler.brawlhalla.ranking.data.dto.StatDetailDto
import com.nickoehler.brawlhalla.ranking.data.mappers.toRankings
import com.nickoehler.brawlhalla.ranking.data.mappers.toStatDetail
import com.nickoehler.brawlhalla.ranking.data.util.constructRankingsUrl
import com.nickoehler.brawlhalla.ranking.domain.Bracket
import com.nickoehler.brawlhalla.ranking.domain.Ranking
import com.nickoehler.brawlhalla.ranking.domain.RankingsDataSource
import com.nickoehler.brawlhalla.ranking.domain.Region
import com.nickoehler.brawlhalla.ranking.domain.StatDetail
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class RemoteRankingDataSource(
    private val httpClient: HttpClient
) : RankingsDataSource {
    override suspend fun getRankings(
        bracket: Bracket,
        regions: Region,
        page: Int,
        name: String?
    ): Result<List<Ranking>, NetworkError> {
        return safeCall<List<RankingsDto>> {
            httpClient.get(
                constructRankingsUrl(bracket, regions, page)
            ) {
                if (name != null) {
                    parameter("name", name)
                }
            }
        }.map { response ->
            response.map {
                it.toRankings()
            }
        }
    }

    override suspend fun getStat(brawlhallaId: Int): Result<StatDetail, NetworkError> {
        return safeCall<StatDetailDto> {
            httpClient.get(
                "/player/$brawlhallaId/stats"
            )
        }.map { response ->
            response.toStatDetail()
        }
    }

}