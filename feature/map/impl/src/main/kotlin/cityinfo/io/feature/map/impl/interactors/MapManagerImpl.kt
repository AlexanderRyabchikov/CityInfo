package cityinfo.io.feature.map.impl.interactors

import android.content.Context
import cityinfo.io.core.map.api.managers.MapManager
import cityinfo.io.core.map.widgets.utils.MapKit
import cityinfo.io.feature.map.impl.BuildConfig

class MapManagerImpl(
    private val ctx: Context,
) : MapManager {

    override fun initMap() {
        check(BuildConfig.YANDEX_MAP_KEY.isNotBlank()) {
            "YANDEX_MAP_KEY не задан. Добавьте его в local.properties — см. local.properties.example"
        }
        MapKit.register(BuildConfig.YANDEX_MAP_KEY)
        MapKit.init(ctx)
    }
}
