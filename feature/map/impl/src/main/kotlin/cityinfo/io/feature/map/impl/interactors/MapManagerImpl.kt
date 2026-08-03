package cityinfo.io.feature.map.impl.interactors

import android.content.Context
import cityinfo.io.core.map.api.managers.MapManager
import cityinfo.io.core.map.widgets.utils.MapKit

private const val YANDEX_API_KEY = "79c34832-4656-447a-8518-17b71e509ed9"

class MapManagerImpl(
    private val ctx: Context,
) : MapManager {

    override fun initMap() {
        MapKit.register(YANDEX_API_KEY)
        MapKit.init(ctx)
    }
}