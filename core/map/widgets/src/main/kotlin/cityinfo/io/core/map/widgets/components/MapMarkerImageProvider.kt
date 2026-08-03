package cityinfo.io.core.map.widgets.components

import com.yandex.runtime.image.ImageProvider

interface MapMarkerImageProvider {

    fun imageProvider(data: MapMarkerImageProviderData): ImageProvider
}

interface MapMarkerImageProviderData
