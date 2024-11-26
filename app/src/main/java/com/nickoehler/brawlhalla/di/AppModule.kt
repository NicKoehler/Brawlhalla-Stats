package com.nickoehler.brawlhalla.di

import com.nickoehler.brawlhalla.core.data.networking.HttpClientFactory
import com.nickoehler.brawlhalla.legends.data.RemoteLegendsDataSource
import com.nickoehler.brawlhalla.legends.domain.LegendsDataSource
import com.nickoehler.brawlhalla.legends.presentation.LegendsViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    single<LegendsDataSource> { RemoteLegendsDataSource(get()) }
    viewModel { LegendsViewModel(get()) }
}