package cityinfo.io.core.map.widgets.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.DpSize
import cityinfo.io.core.map.widgets.controller.MapControllerEffect
import cityinfo.io.core.map.widgets.controller.YandexMapController
import cityinfo.io.core.map.api.models.Marker
import cityinfo.io.core.map.api.models.MarkerData
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.Cluster
import com.yandex.mapkit.map.ClusterListener
import com.yandex.mapkit.map.ClusterTapListener
import com.yandex.mapkit.map.ClusterizedPlacemarkCollection
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.MapWindow
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.runtime.image.ImageProvider
import kotlinx.coroutines.launch

@Composable
fun rememberMarkerCluster(): ComposeMarkerCluster {
    return remember { ComposeMarkerCluster() }
}

class ComposeMarkerCluster internal constructor() {
    internal val markers = hashMapOf<MarkerKey, Marker>()
    internal val placemarks = hashMapOf<MarkerKey, PlacemarkMapObject>()

    internal fun clear() {
        markers.clear()
        placemarks.clear()
    }

    data class MarkerKey(val longitude: Double, val latitude: Double) {
        constructor(point: Point) : this(point.longitude, point.latitude)
    }
}

@Composable
fun rememberMapObjectListener(
    clusterMarker: ComposeMarkerCluster = rememberMarkerCluster(),
    onMarkerClick: (Marker) -> Unit,
): MapObjectTapListener {
    return remember(clusterMarker) {
        MapObjectTapListener { obj, _ ->
            if (obj is PlacemarkMapObject) {
                clusterMarker.markers[ComposeMarkerCluster.MarkerKey(obj.geometry)]?.also(onMarkerClick)
                true
            } else {
                false
            }
        }
    }
}

class ClusterClickScope internal constructor(
    val mapWindow: MapWindow,
    val geometry: Geometry,
)

@Composable
fun rememberClusterTapListener(
    controller: YandexMapController,
    onClusterClick: ClusterClickScope.() -> Unit = {},
): ClusterTapListener {
    return remember(controller) {
        ClusterTapListener { cluster ->
            runCatching {
                val polyline = cluster.placemarks.map { it.geometry }.let { Polyline(it) }
                controller.mapWindow?.also { window ->
                    val geometry = Geometry.fromPolyline(Polyline(polyline.points))
                    val scope = ClusterClickScope(window, geometry)
                    onClusterClick(scope)
                }
            }
            true
        }
    }
}

class ClusterContentScope internal constructor(
    val markers: List<Marker>,
    val camera: CameraPosition,
)

@Composable
fun rememberClusterListener(
    controller: YandexMapController,
    onCreate: (Cluster) -> Unit,
): ClusterListener {
    return remember(controller) {
        ClusterListener(onCreate)
    }
}

@Composable
fun MapMarkerClusterEffect(
    controller: YandexMapController,
    markers: List<Marker>,
    isShowPins: Boolean = true,
    isEnabled: Boolean = true,
    onClusterClick: ClusterClickScope.() -> Unit,
    onMarkerClick: (Marker) -> Unit,
    clusterContent: ClusterContentScope.() -> ImageProvider,
    clusterRadius: Double = CLUSTER_RADIUS,
    clusterZoom: Int = CLUSTER_ZOOM,
    iconStyle: IconStyle = IconStyle(),
    clusterMarker: ComposeMarkerCluster = rememberMarkerCluster(),
    onCompletedAdd: () -> Unit = {},
    markerContent: (data: MarkerData) -> ImageProvider?,
) {
    val clusterTapListener = rememberClusterTapListener(controller, onClusterClick)

    val clusterListener = rememberClusterListener(controller) { cluster ->
        val cam = controller.mapWindow?.map?.cameraPosition ?: CameraPosition()
        val data = cluster.placemarks.mapNotNull {
            clusterMarker.markers[ComposeMarkerCluster.MarkerKey(it.geometry)]
        }
        val clusterContentScope = ClusterContentScope(data, cam)
        val clusterProvider = clusterContent(clusterContentScope)

        cluster.appearance.setIcon(clusterProvider, iconStyle)
        cluster.addClusterTapListener(clusterTapListener)
    }

    val tapListener = rememberMapObjectListener(clusterMarker, onMarkerClick)

    var collectionsPins: ClusterizedPlacemarkCollection? by remember { mutableStateOf(null) }

    if (isEnabled) MapControllerEffect(
        key1 = clusterMarker,
        key2 = markers,
        key3 = isShowPins,
        controller = controller,
        dispose = { window ->
            collectionsPins?.also { collection ->
                if (collection.isValid) collection.clear()
            }
            clusterMarker.clear()
        },
    ) { window ->
        collectionsPins?.also { collection ->
            if (collection.isValid) collection.clear()
        }
        clusterMarker.clear()

        collectionsPins = window.map.mapObjects.addCollection()
            .addClusterizedPlacemarkCollection(clusterListener)
            .apply { addTapListener(tapListener) }

        collectionsPins?.also { collection ->

            if (collection.isValid) {
                collection.isVisible = isShowPins
            }

            val pins = markers
                .groupBy { it.data }
                .map { (key, value) ->
                    val mapped = value.map { marker ->
                        val point = Point(marker.latitude, marker.longitude)
                        clusterMarker.markers[ComposeMarkerCluster.MarkerKey(point)] = marker
                        point
                    }
                    val createProvider = markerContent(key)
                    mapped to createProvider
                }

            pins.forEach { (points, provider) ->
                if (provider != null && collection.isValid) {
                    collection.addPlacemarks(points, provider, iconStyle).forEach { placemark ->
                        clusterMarker.placemarks[ComposeMarkerCluster.MarkerKey(placemark.geometry)] = placemark
                    }
                    collection.clusterPlacemarks(clusterRadius, clusterZoom)
                }
            }

            if (collection.isValid) {
                collection.clusterPlacemarks(clusterRadius, clusterZoom)
            }

            onCompletedAdd()

            if (collection.isValid) {
                collection.isVisible = isShowPins
            }
        }
    }
}

@Composable
fun MapMarkerIconEffect(
    controller: YandexMapController,
    clusterMarker: ComposeMarkerCluster,
    key: Any?,
    iconStyle: IconStyle = IconStyle(),
    markerContent: (data: MarkerData) -> ImageProvider?,
) {
    MapControllerEffect(
        controller = controller,
        key1 = key,
    ) {
        clusterMarker.placemarks.forEach { (markerKey, placemark) ->
            val marker = clusterMarker.markers[markerKey] ?: return@forEach
            val provider = markerContent(marker.data) ?: return@forEach
            if (placemark.isValid) placemark.setIcon(provider, iconStyle)
        }
    }
}

private const val CLUSTER_RADIUS = 60.0
private const val CLUSTER_ZOOM = 18
