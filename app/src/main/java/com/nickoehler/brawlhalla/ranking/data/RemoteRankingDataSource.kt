package com.nickoehler.brawlhalla.ranking.data

import com.nickoehler.brawlhalla.core.data.networking.safeCall
import com.nickoehler.brawlhalla.core.domain.util.NetworkError
import com.nickoehler.brawlhalla.core.domain.util.Result
import com.nickoehler.brawlhalla.core.domain.util.map
import com.nickoehler.brawlhalla.ranking.data.dto.PlayerGuildResponseDto
import com.nickoehler.brawlhalla.ranking.data.dto.PlayerStatsDto
import com.nickoehler.brawlhalla.ranking.data.dto.RankingDetailDto
import com.nickoehler.brawlhalla.ranking.data.dto.RankingResponseDto
import com.nickoehler.brawlhalla.ranking.data.dto.TeamsResponseDto
import com.nickoehler.brawlhalla.ranking.data.mappers.toPlayerGuild
import com.nickoehler.brawlhalla.ranking.data.mappers.toRanking
import com.nickoehler.brawlhalla.ranking.data.mappers.toRankingDetail
import com.nickoehler.brawlhalla.ranking.data.mappers.toStatDetail
import com.nickoehler.brawlhalla.ranking.data.mappers.toTeamDetail
import com.nickoehler.brawlhalla.ranking.data.mappers.toUrlString
import com.nickoehler.brawlhalla.ranking.domain.GameMode
import com.nickoehler.brawlhalla.ranking.domain.Ranking
import com.nickoehler.brawlhalla.ranking.domain.RankingDetail
import com.nickoehler.brawlhalla.ranking.domain.RankingsDataSource
import com.nickoehler.brawlhalla.ranking.domain.Region
import com.nickoehler.brawlhalla.ranking.domain.StatDetail
import com.nickoehler.brawlhalla.ranking.domain.TeamDetail
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class RemoteRankingDataSource(
    private val httpClient: HttpClient,
) : RankingsDataSource {
    override suspend fun getRankings(
        gameMode: GameMode,
        region: Region,
        page: Int,
        name: String?
    ): Result<List<Ranking>, NetworkError> {
        return safeCall<RankingResponseDto> {
            httpClient.get(
                "/v1/leaderboard/ranked"
            ) {
                if (name != null) {
                    parameter("search", name)
                }
                parameter("game_mode", gameMode.toUrlString())
                parameter("region", region.toUrlString())
                parameter("page", page.toString())
                parameter("max_results", 50)
            }
        }.map { response ->
            response.rankings.map {
                it.toRanking()
            }
        }
    }

    override suspend fun getStat(brawlhallaId: Long): Result<StatDetail, NetworkError> {
        return coroutineScope {
            val stats = async {
                safeCall<PlayerStatsDto> {
                    httpClient.get("/v1/player/stats") {
                        parameter("brawlhalla_id", brawlhallaId)
                    }
                }
            }

            val guild = async {
                safeCall<PlayerGuildResponseDto> {
                    httpClient.get("/v1/player/guild") {
                        parameter("brawlhalla_id", brawlhallaId)
                    }
                }
            }

            val guildData = (guild.await() as? Result.Success)
                ?.data
                ?.guild
                ?.toPlayerGuild()
            stats.await().map { it.toStatDetail(guildData) }
        }
    }

    override suspend fun getRanked(brawlhallaId: Long): Result<RankingDetail, NetworkError> {
        return safeCall<RankingDetailDto> {
            httpClient.get(
                "/v1/player/stats"
            ) {
                parameter("brawlhalla_id", brawlhallaId)
                parameter("mode", "ranked_1v1")
            }
        }.map { response ->
            response.toRankingDetail()
        }
    }

    override suspend fun getTeams(brawlhallaId: Long): Result<List<TeamDetail>, NetworkError> {
        return safeCall<TeamsResponseDto> {
            httpClient.get("/v1/player/teams") {
                parameter("brawlhalla_id", brawlhallaId)
            }
        }.map { response -> response.teams.ranked2v2.map { it.toTeamDetail() } }
    }
}