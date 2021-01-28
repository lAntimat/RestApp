package ru.lantimat.my

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.lantimat.my.data.appModule
import ru.lantimat.my.data.dataModule
import timber.log.Timber

class App : Application() {

    private val isDebug = BuildConfig.DEBUG

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initKoin()
        initJSR310Backport()
    }

    private fun initTimber() {
        if (isDebug) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initKoin() {
        startKoin {
            androidLogger(if (isDebug) Level.DEBUG else Level.ERROR)
            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    dataModule,
                    //presentationModule
                )
            )
        }
    }

    private fun initJSR310Backport() {
        // load timezones for JSR310
        //AndroidThreeTen.init(this)
    }
}