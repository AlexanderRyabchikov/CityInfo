package cityinfo.io.core.map.widgets.controller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.yandex.mapkit.map.MapWindow
import kotlinx.atomicfu.locks.reentrantLock
import kotlinx.atomicfu.locks.withLock

@Composable
fun rememberYandexMapController(): YandexMapController {
    return remember { YandexMapController() }
}

class YandexMapController internal constructor() {
    internal val mapWindowOwner = MapWindowOwner()
    val mapWindow: MapWindow? get() = mapWindowOwner.mapWindow
}

internal class MapWindowOwner(private val callback: ((MapWindow?) -> Unit)? = null) {

    private val lock = reentrantLock()
    internal var mapWindow: MapWindow? by mutableStateOf(null)
        private set

    internal fun setMapWindow(mapWindow: MapWindow?) {
        lock.withLock {
            if (this.mapWindow == null && mapWindow == null) return
            if (this.mapWindow == mapWindow) return
            if (this.mapWindow != null && mapWindow != null) {
                error("YandexMapController may only be associated with one MapView at a time")
            }
            this.mapWindow = mapWindow
            callback?.invoke(mapWindow)
        }
    }
}
