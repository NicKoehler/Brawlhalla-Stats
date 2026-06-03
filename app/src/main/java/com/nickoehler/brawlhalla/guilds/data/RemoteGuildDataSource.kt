package com.nickoehler.brawlhalla.guilds.data

import com.nickoehler.brawlhalla.core.data.networking.safeCall
import com.nickoehler.brawlhalla.core.domain.util.NetworkError
import com.nickoehler.brawlhalla.core.domain.util.Result
import com.nickoehler.brawlhalla.core.domain.util.map
import com.nickoehler.brawlhalla.guilds.data.dto.GuildDetailDto
import com.nickoehler.brawlhalla.guilds.data.dto.GuildMemberResponseDto
import com.nickoehler.brawlhalla.guilds.data.mappers.toGuildDetail
import com.nickoehler.brawlhalla.guilds.data.mappers.toGuildMember
import com.nickoehler.brawlhalla.guilds.domain.GuildDataSource
import com.nickoehler.brawlhalla.guilds.domain.GuildDetail
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class RemoteGuildDataSource(
    private val httpClient: HttpClient,
) : GuildDataSource {
    override suspend fun getGuild(guildId: Long): Result<GuildDetail, NetworkError> {
        return coroutineScope {
            val guild = async {
                safeCall<GuildDetailDto> {
                    httpClient.get("/v1/guild/stats") {
                        parameter("guild_id", guildId)
                    }
                }
            }

            val members = async {
                safeCall<GuildMemberResponseDto> {
                    httpClient.get("/v1/guild/members") {
                        parameter("guild_id", guildId)
                    }
                }.map { it.guildMembers }
            }

            val guildMembers = (members.await() as? Result.Success)?.data?.map {
                it.toGuildMember()
            }

            guild.await().map { it.toGuildDetail(members = guildMembers.orEmpty()) }
        }
    }
}