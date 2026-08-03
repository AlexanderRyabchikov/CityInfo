package cityinfo.io.feature.map.impl.screens.map.components

import android.graphics.PointF
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import cityinfo.io.core.map.api.models.MarkerData
import cityinfo.io.core.map.widgets.components.MapMarkerClusterEffect
import cityinfo.io.core.map.widgets.components.MapMarkerIconEffect
import cityinfo.io.core.map.widgets.components.rememberMarkerCluster
import cityinfo.io.core.map.widgets.controller.YandexMapController
import cityinfo.io.feature.map.api.models.CityMap
import cityinfo.io.feature.map.api.models.CityMapMarkerData
import cityinfo.io.feature.map.impl.screens.map.CityMapHandler
import cityinfo.io.feature.map.impl.screens.map.CityMapState
import cityinfo.io.feature.map.impl.screens.map.components.markers.CityMapMarkerImage
import cityinfo.io.feature.map.impl.screens.map.components.markers.MapMarker
import com.yandex.mapkit.Animation
import com.yandex.mapkit.map.IconStyle
import com.yandex.runtime.image.ImageProvider

private const val ANCHOR_X = 0.5f
private const val ANCHOR_Y = 1f
private const val CAMERA_ANIMATION_DURATION = 0.5f

@Composable
internal fun CityMapMarkers(
    controller: YandexMapController,
    state: CityMapState,
    handler: CityMapHandler,
) {
    val ctx = LocalContext.current
    val marker = remember { MapMarker(ctx) }
    val iconStyle = remember { IconStyle().setAnchor(PointF(ANCHOR_X, ANCHOR_Y)) }
    val clusterMarker = rememberMarkerCluster()

    val selectedCityId = state.selectedCity?.data?.id
    val icons = remember(state.cities) { mutableMapOf<CityMapMarkerImage, ImageProvider>() }

    val pinIcon = { data: MarkerData ->
        (data as? CityMapMarkerData)?.let {
            val image = CityMapMarkerImage.Pin(
                name = it.name,
                isSelected = it.id == selectedCityId,
            )
            icons.getOrPut(image) { marker.imageProvider(image) }
        }
    }

    MapMarkerClusterEffect(
        controller = controller,
        markers = state.cities,
        iconStyle = iconStyle,
        clusterMarker = clusterMarker,
        onMarkerClick = { if (it is CityMap) handler.onSelectCity(it) },
        onClusterClick = {
            mapWindow.map.move(
                mapWindow.map.cameraPosition(geometry),
                Animation(Animation.Type.SMOOTH, CAMERA_ANIMATION_DURATION),
                null,
            )
        },
        clusterContent = {
            marker.imageProvider(CityMapMarkerImage.Cluster(count = markers.size))
        },
        markerContent = pinIcon,
    )

    MapMarkerIconEffect(
        controller = controller,
        clusterMarker = clusterMarker,
        key = selectedCityId,
        iconStyle = iconStyle,
        markerContent = pinIcon,
    )
}