package cityinfo.io.core.map.widgets.components

import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import cityinfo.io.core.map.widgets.R
import cityinfo.io.core.map.widgets.controller.YandexMapController
import cityinfo.io.core.map.widgets.controller.rememberYandexMapController
import cityinfo.io.core.map.widgets.utils.MapKit
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

@Composable
fun YandexMap(
    modifier: Modifier = Modifier,
    controller: YandexMapController = rememberYandexMapController(),
    update: (MapView) -> Unit = {},
) {
    MapView(
        modifier = modifier,
        update = {
            controller.mapWindowOwner.setMapWindow(it.mapWindow)
            update(it)
        },
        onRelease = { controller.mapWindowOwner.setMapWindow(null) },
    )
}

@Composable
internal fun MapView(
    modifier: Modifier = Modifier,
    onRelease: (MapView) -> Unit,
    update: (MapView) -> Unit,
) {
    val mapView = rememberMapView()
    var cameraPosition by rememberLastCameraPosition(mapView.mapWindow.map.cameraPosition)

    Box(modifier = modifier) {
        AndroidView(
            modifier = Modifier.matchParentSize(),
            factory = { mapView },
            onRelease = {
                onRelease(mapView)
                mapView.onStop()
            },
            update = {
                update(mapView)
            },
        )
    }
    MapLifecycle(
        onDispose = {
            cameraPosition = mapView.mapWindow.map.cameraPosition.last
            mapView.onStop()
            MapKit.onStop()
        },
        onStart = {
            mapView.mapWindow.map.move(cameraPosition.camera)
            MapKit.onStart()
            mapView.onStart()
        },
        onStop = {
            cameraPosition = mapView.mapWindow.map.cameraPosition.last
            mapView.onStop()
            MapKit.onStop()
        },
    )
}

@Composable
fun rememberLastCameraPosition(current: CameraPosition): MutableState<MapLastCameraPosition> {
    return rememberSaveable(saver = MapLastCameraPositionSaver) { mutableStateOf(current.last) }
}

private val CameraPosition.last
    get() = MapLastCameraPosition(
        latitude = target.latitude,
        longitude = target.longitude,
        zoom = zoom,
        azimuth = azimuth,
        tilt = tilt,
    )

private val MapLastCameraPosition.camera
    get() = CameraPosition(Point(latitude, longitude), zoom, azimuth, tilt)

class MapLastCameraPosition(
    val latitude: Double,
    val longitude: Double,
    val zoom: Float,
    val azimuth: Float,
    val tilt: Float,
)

val MapLastCameraPositionSaver = mapSaver(
    save = { state ->
        val cameraPosition = state.value
        mapOf(
            "latitude" to cameraPosition.latitude,
            "longitude" to cameraPosition.longitude,
            "zoom" to cameraPosition.zoom,
            "azimuth" to cameraPosition.azimuth,
            "tilt" to cameraPosition.tilt,
        )
    },
    restore = { map ->
        mutableStateOf(
            MapLastCameraPosition(
                latitude = map["latitude"] as Double,
                longitude = map["longitude"] as Double,
                zoom = map["zoom"] as Float,
                azimuth = map["azimuth"] as Float,
                tilt = map["tilt"] as Float,
            ),
        )
    },
)

@Composable
private fun MapLifecycle(
    onStart: () -> Unit = {},
    onStop: () -> Unit = {},
    onDispose: () -> Unit = {},
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val currentOnDispose by rememberUpdatedState(onDispose)
    val currentOnStart by rememberUpdatedState(onStart)
    val currentOnStop by rememberUpdatedState(onStop)
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> currentOnStart()
                Lifecycle.Event.ON_STOP -> currentOnStop()
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            currentOnDispose()
        }
    }
}

@Composable
private fun rememberMapView(isCreateByGLIndexFirst: Boolean = true): MapView {
    val context = LocalContext.current

    val view = remember {
        if (isCreateByGLIndexFirst) {
            runCatching {
                val view = View.inflate(context, R.layout.map_view, null)
                view.findViewById<MapView>(R.id.mapView)
            }.getOrElse {
                MapView(context)
            }
        } else MapView(context)
    }

    return view
}