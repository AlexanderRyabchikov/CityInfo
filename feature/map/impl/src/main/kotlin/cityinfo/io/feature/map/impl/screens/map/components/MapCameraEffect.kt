package cityinfo.io.feature.map.impl.screens.map.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import cityinfo.io.feature.map.impl.data.internal.CityMapCamera
import cityinfo.io.feature.map.impl.screens.map.CityMapHandler
import cityinfo.io.core.map.widgets.controller.MapControllerEffect
import cityinfo.io.core.map.widgets.controller.YandexMapController
import com.yandex.mapkit.geometry.Geo
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.VisibleRegion

@Composable
internal fun MapCameraEffect(
    controller: YandexMapController,
    handler: CityMapHandler,
) {
    var isStartPositionApplied by rememberSaveable { mutableStateOf(false) }

    val cameraListener = remember {
        CameraListener { map, camera, _, isFinished ->
            if (isFinished && map.isValid) {
                handler.onCameraChanged(
                    latitude = camera.target.latitude,
                    longitude = camera.target.longitude,
                    radius = map.visibleRegion.radius,
                )
            }
        }
    }

    MapControllerEffect(
        controller = controller,
        dispose = { window ->
            if (window.map.isValid) window.map.removeCameraListener(cameraListener)
        },
    ) { window ->
        if (!isStartPositionApplied) {
            isStartPositionApplied = true
            window.map.move(CityMapCamera.DEFAULT.cameraPosition)
        }
        window.map.addCameraListener(cameraListener)
    }
}

private val CityMapCamera.cameraPosition
    get() = CameraPosition(
        Point(latitude, longitude),
        CityMapCamera.DEFAULT_ZOOM,
        0f,
        0f,
    )

private val VisibleRegion.radius
    get() = Geo.distance(topLeft, bottomRight) / 2