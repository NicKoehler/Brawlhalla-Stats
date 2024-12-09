package com.nickoehler.brawlhalla.ranking.data

import com.nickoehler.brawlhalla.core.data.networking.safeCall
import com.nickoehler.brawlhalla.core.domain.util.NetworkError
import com.nickoehler.brawlhalla.core.domain.util.Result
import com.nickoehler.brawlhalla.core.domain.util.map
import com.nickoehler.brawlhalla.ranking.data.dto.ClanDetailDto
import com.nickoehler.brawlhalla.ranking.data.dto.RankingDetailDto
import com.nickoehler.brawlhalla.ranking.data.dto.RankingSoloDto
import com.nickoehler.brawlhalla.ranking.data.dto.RankingTeamDto
import com.nickoehler.brawlhalla.ranking.data.dto.StatDetailDto
import com.nickoehler.brawlhalla.ranking.data.mappers.toClanDetail
import com.nickoehler.brawlhalla.ranking.data.mappers.toRanking
import com.nickoehler.brawlhalla.ranking.data.mappers.toRankingDetail
import com.nickoehler.brawlhalla.ranking.data.mappers.toStatDetail
import com.nickoehler.brawlhalla.ranking.data.util.constructRankingsUrl
import com.nickoehler.brawlhalla.ranking.domain.Bracket
import com.nickoehler.brawlhalla.ranking.domain.ClanDetail
import com.nickoehler.brawlhalla.ranking.domain.Ranking
import com.nickoehler.brawlhalla.ranking.domain.RankingDetail
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

        when (bracket) {
            Bracket.ONE_VS_ONE, Bracket.ROTATING -> return safeCall<List<RankingSoloDto>> {
                httpClient.get(
                    constructRankingsUrl(bracket, regions, page)
                ) {
                    if (name != null) {
                        parameter("name", name)
                    }
                }
            }.map { response ->
                response.map {
                    it.toRanking()
                }
            }

            Bracket.TWO_VS_TWO -> return safeCall<List<RankingTeamDto>> {
                httpClient.get(
                    constructRankingsUrl(bracket, regions, page)
                ) {
                    if (name != null) {
                        parameter("name", name)
                    }
                }
            }.map { response ->
                response.map {
                    it.toRanking()
                }
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

    override suspend fun getRanked(brawlhallaId: Int): Result<RankingDetail, NetworkError> {
        return safeCall<RankingDetailDto> {
            httpClient.get(
                "/player/$brawlhallaId/ranked"
            )
        }.map { response ->
            response.toRankingDetail()
        }
    }

    override suspend fun getClan(clanId: Int): Result<ClanDetail, NetworkError> {
        return safeCall<ClanDetailDto> {
            httpClient.get("/clan/$clanId")
        }.map { it.toClanDetail() }
    }
}