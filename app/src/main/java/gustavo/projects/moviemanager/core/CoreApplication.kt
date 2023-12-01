package gustavo.projects.moviemanager.core

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import gustavo.projects.moviemanager.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class CoreApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Init Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
