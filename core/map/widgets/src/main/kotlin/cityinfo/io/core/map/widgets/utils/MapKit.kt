package cityinfo.io.core.map.widgets.utils

import android.content.Context
import com.yandex.mapkit.MapKitFactory

object MapKit {

    fun register(apiKey: String) {
        MapKitFactory.setApiKey(apiKey)
    }

    fun init(context: Context) {
        MapKitFactory.initialize(context)
    }

    fun onStart() {
        MapKitFactory.getInstance().onStart()
    }

    fun onStop() {
        MapKitFactory.getInstance().onStop()
    }
}