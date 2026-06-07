package com.nickoehler.brawlhalla

import android.app.Application
import com.nickoehler.brawlhalla.di.appModule
import com.nickoehler.brawlhalla.widgets.WidgetUpdateManager
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class BrawlhallaApp : Application() {

    private val widgetUpdateManager by inject<WidgetUpdateManager>()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BrawlhallaApp)
            androidLogger()
            modules(appModule)
        }
        widgetUpdateManager.syncAll()
    }
}
