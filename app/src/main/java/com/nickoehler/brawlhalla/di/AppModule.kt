package com.nickoehler.brawlhalla.di

import com.nickoehler.brawlhalla.core.data.networking.HttpClientFactory
import com.nickoehler.brawlhalla.legends.data.RemoteLegendsDataSource
import com.nickoehler.brawlhalla.legends.domain.LegendsDataSource
import com.nickoehler.brawlhalla.legends.presentation.LegendsViewModel
import com.nickoehler.brawlhalla.ranking.data.RemoteRankingDataSource
import com.nickoehler.brawlhalla.ranking.domain.RankingsDataSource
import com.nickoehler.brawlhalla.ranking.presentation.RankingViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    single<LegendsDataSource> { RemoteLegendsDataSource(get()) }
    single<RankingsDataSource> { RemoteRankingDataSource(get()) }
    viewModel { LegendsViewModel(get()) }
    viewModel { RankingViewModel(get()) }
}