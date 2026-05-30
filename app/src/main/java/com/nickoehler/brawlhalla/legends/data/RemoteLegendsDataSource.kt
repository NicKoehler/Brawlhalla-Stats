package com.nickoehler.brawlhalla.legends.data

import com.nickoehler.brawlhalla.core.data.networking.safeCall
import com.nickoehler.brawlhalla.core.domain.util.NetworkError
import com.nickoehler.brawlhalla.core.domain.util.Result
import com.nickoehler.brawlhalla.core.domain.util.map
import com.nickoehler.brawlhalla.legends.data.dto.LegendsResponseDto
import com.nickoehler.brawlhalla.legends.data.mappers.toLegendDetail
import com.nickoehler.brawlhalla.legends.domain.LegendDetail
import com.nickoehler.brawlhalla.legends.domain.LegendsDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteLegendsDataSource(
    private val httpClient: HttpClient
) : LegendsDataSource {
    override suspend fun getLegends(): Result<List<LegendDetail>, NetworkError> {
        return safeCall<LegendsResponseDto> {
            httpClient.get("/v1/static/legends?max_results=100")
        }.map { response ->
            response.legends.filter { it.legendId != 2L }.map {
                it.toLegendDetail()
            }
        }
    }
}