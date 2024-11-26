package com.nickoehler.brawlhalla.legends.data

import com.nickoehler.brawlhalla.core.data.networking.safeCall
import com.nickoehler.brawlhalla.core.domain.util.NetworkError
import com.nickoehler.brawlhalla.core.domain.util.Result
import com.nickoehler.brawlhalla.core.domain.util.map
import com.nickoehler.brawlhalla.legends.data.dto.LegendDetailDto
import com.nickoehler.brawlhalla.legends.data.mappers.toLegend
import com.nickoehler.brawlhalla.legends.data.mappers.toLegendDetail
import com.nickoehler.brawlhalla.legends.domain.LegendDetail
import com.nickoehler.brawlhalla.legends.domain.LegendsDataSource
import com.nickoehler.brawlhalla.legends.domain.Legend
import com.nickoehler.brawltool.model.LegendDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteLegendsDataSource(
    private val httpClient: HttpClient
) : LegendsDataSource {
    override suspend fun getLegends(): Result<List<Legend>, NetworkError> {
        return safeCall<List<LegendDto>> {
            httpClient.get("/legend/all")
        }.map { response ->
            response.map {
                it.toLegend()
            }
        }
    }

    override suspend fun getLegendDetail(legendId: Int): Result<LegendDetail, NetworkError> {
        return safeCall<LegendDetailDto> {
            httpClient.get(
                "/legend/$legendId"
            )
        }.map { legend ->
            legend.toLegendDetail()
        }
    }

}