package cityinfo.io.feature.map.impl.screens.map.components.markers

import cityinfo.io.core.map.widgets.components.MapMarkerImageProviderData

internal sealed interface CityMapMarkerImage : MapMarkerImageProviderData {

    data class Pin(val name: String, val isSelected: Boolean) : CityMapMarkerImage

    data class Cluster(val count: Int) : CityMapMarkerImage
}