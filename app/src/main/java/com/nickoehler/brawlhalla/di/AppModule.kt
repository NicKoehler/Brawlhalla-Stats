package com.nickoehler.brawlhalla.di

import com.nickoehler.brawlhalla.clans.data.RemoteClanDataSource
import com.nickoehler.brawlhalla.clans.domain.ClanDataSource
import com.nickoehler.brawlhalla.clans.presentation.ClanViewModel
import com.nickoehler.brawlhalla.core.data.DatabaseDataSource
import com.nickoehler.brawlhalla.core.data.database.provideDataBase
import com.nickoehler.brawlhalla.core.data.networking.HttpClientFactory
import com.nickoehler.brawlhalla.core.domain.LocalDataSource
import com.nickoehler.brawlhalla.favorites.presentation.FavoritesViewModel
import com.nickoehler.brawlhalla.info.presentation.InfoViewModel
import com.nickoehler.brawlhalla.legends.data.RemoteLegendsDataSource
import com.nickoehler.brawlhalla.legends.domain.LegendsDataSource
import com.nickoehler.brawlhalla.legends.presentation.LegendsViewModel
import com.nickoehler.brawlhalla.ranking.data.RemoteRankingDataSource
import com.nickoehler.brawlhalla.ranking.domain.RankingsDataSource
import com.nickoehler.brawlhalla.ranking.presentation.RankingViewModel
import com.nickoehler.brawlhalla.ranking.presentation.StatDetailViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    single { provideDataBase(get()) }
    singleOf(::DatabaseDataSource) { bind<LocalDataSource>() }
    singleOf(::RemoteLegendsDataSource) { bind<LegendsDataSource>() }
    singleOf(::RemoteRankingDataSource) { bind<RankingsDataSource>() }
    singleOf(::RemoteClanDataSource) { bind<ClanDataSource>() }
    viewModelOf(::LegendsViewModel)
    viewModelOf(::StatDetailViewModel)
    viewModelOf(::RankingViewModel)
    viewModelOf(::ClanViewModel)
    viewModelOf(::FavoritesViewModel)
    viewModelOf(::InfoViewModel)
}