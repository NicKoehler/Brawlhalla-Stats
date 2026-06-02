package com.nickoehler.brawlhalla.guilds.data

import com.nickoehler.brawlhalla.core.data.networking.safeCall
import com.nickoehler.brawlhalla.core.domain.util.NetworkError
import com.nickoehler.brawlhalla.core.domain.util.Result
import com.nickoehler.brawlhalla.core.domain.util.map
import com.nickoehler.brawlhalla.guilds.data.dto.GuildDetailDto
import com.nickoehler.brawlhalla.guilds.data.mappers.toClanDetail
import com.nickoehler.brawlhalla.guilds.domain.ClanDataSource
import com.nickoehler.brawlhalla.guilds.domain.ClanDetail
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteGuildDataSource(
    private val httpClient: HttpClient,
) : ClanDataSource {
    override suspend fun getClan(clanId: Long): Result<ClanDetail, NetworkError> {
        return safeCall<GuildDetailDto> {
            httpClient.get("/clan/$clanId")
        }.map { it.toClanDetail() }
    }
}