package com.nickoehler.brawlhalla.clans.domain

import com.nickoehler.brawlhalla.core.domain.util.NetworkError
import com.nickoehler.brawlhalla.core.domain.util.Result

interface ClanDataSource {

    suspend fun getClan(clanId: Long): Result<ClanDetail, NetworkError>

}