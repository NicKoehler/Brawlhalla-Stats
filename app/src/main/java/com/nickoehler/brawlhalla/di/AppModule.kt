package com.nickoehler.brawlhalla.di

import AppLocaleManager
import com.nickoehler.brawlhalla.core.data.DatabaseDataSource
import com.nickoehler.brawlhalla.core.data.database.provideDataBase
import com.nickoehler.brawlhalla.core.data.datastore.Settings
import com.nickoehler.brawlhalla.core.data.networking.HttpClientFactory
import com.nickoehler.brawlhalla.core.domain.LocalDataSource
import com.nickoehler.brawlhalla.core.domain.LocalPreferences
import com.nickoehler.brawlhalla.core.presentation.ThemeViewModel
import com.nickoehler.brawlhalla.favorites.presentation.FavoritesViewModel
import com.nickoehler.brawlhalla.guilds.data.RemoteGuildDataSource
import com.nickoehler.brawlhalla.guilds.domain.GuildDataSource
import com.nickoehler.brawlhalla.guilds.presentation.GuildViewModel
import com.nickoehler.brawlhalla.legends.data.RemoteLegendsDataSource
import com.nickoehler.brawlhalla.legends.domain.LegendsDataSource
import com.nickoehler.brawlhalla.legends.presentation.LegendsViewModel
import com.nickoehler.brawlhalla.ranking.data.RemoteRankingDataSource
import com.nickoehler.brawlhalla.ranking.domain.RankingsDataSource
import com.nickoehler.brawlhalla.ranking.presentation.RankingViewModel
import com.nickoehler.brawlhalla.ranking.presentation.StatDetailViewModel
import com.nickoehler.brawlhalla.settings.presentation.SettingsViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    single { provideDataBase(get()) }
    singleOf(::Settings) { bind<LocalPreferences>() }
    singleOf(::DatabaseDataSource) { bind<LocalDataSource>() }
    singleOf(::RemoteLegendsDataSource) { bind<LegendsDataSource>() }
    singleOf(::RemoteRankingDataSource) { bind<RankingsDataSource>() }
    singleOf(::RemoteGuildDataSource) { bind<GuildDataSource>() }
    singleOf(::AppLocaleManager)
    viewModelOf(::ThemeViewModel)
    viewModelOf(::LegendsViewModel)
    viewModelOf(::StatDetailViewModel)
    viewModelOf(::RankingViewModel)
    viewModelOf(::GuildViewModel)
    viewModelOf(::FavoritesViewModel)
    viewModelOf(::SettingsViewModel)
}