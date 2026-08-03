package cityinfo.io

import android.app.Application
import cityinfo.io.core.map.api.managers.MapManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

class App : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(koinAppModules)
        }

        val mapManager: MapManager by inject()
        mapManager.initMap()
    }
}