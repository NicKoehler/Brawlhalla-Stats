package com.nickoehler.brawlhalla.clans.data

import com.nickoehler.brawlhalla.clans.data.dto.ClanDetailDto
import com.nickoehler.brawlhalla.clans.data.mappers.toClanDetail
import com.nickoehler.brawlhalla.clans.domain.ClanDataSource
import com.nickoehler.brawlhalla.clans.domain.ClanDetail
import com.nickoehler.brawlhalla.core.data.networking.safeCall
import com.nickoehler.brawlhalla.core.domain.util.NetworkError
import com.nickoehler.brawlhalla.core.domain.util.Result
import com.nickoehler.brawlhalla.core.domain.util.map
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteClanDataSource(
    private val httpClient: HttpClient,
) : ClanDataSource {
    override suspend fun getClan(clanId: Long): Result<ClanDetail, NetworkError> {
        return safeCall<ClanDetailDto> {
            httpClient.get("/clan/$clanId")
        }.map { it.toClanDetail() }
    }
}