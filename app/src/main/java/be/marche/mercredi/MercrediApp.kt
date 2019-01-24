package be.marche.mercredi

import android.app.Application
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class MercrediApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModule))

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}