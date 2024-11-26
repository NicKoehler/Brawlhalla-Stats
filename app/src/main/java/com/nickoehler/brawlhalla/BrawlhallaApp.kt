package com.nickoehler.brawlhalla

import android.app.Application
import com.nickoehler.brawlhalla.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BrawlhallaApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BrawlhallaApp)
            androidLogger()
            modules(appModule)
        }
    }
}