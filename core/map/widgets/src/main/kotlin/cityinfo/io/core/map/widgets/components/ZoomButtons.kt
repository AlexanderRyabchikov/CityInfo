package cityinfo.io.core.map.widgets.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cityinfo.io.core.map.widgets.controller.YandexMapController
import cityinfo.io.core.uiKit.R
import cityinfo.io.core.uiKit.base.Colors
import cityinfo.io.core.uiKit.base.Shapes
import cityinfo.io.core.uiKit.extensions.rippleClickable
import com.yandex.mapkit.Animation
import com.yandex.mapkit.map.CameraPosition
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration.Companion.seconds

private val ButtonSize = 52.dp
private val IconSize = 20.dp
private val ShadowElevation = 12.dp

@Composable
fun ZoomButtons(
    controller: YandexMapController,
) {
    val mutex = remember { Mutex() }
    val scope = rememberCoroutineScope()
    val zoom = fun(isPlus: Boolean) {
        if (!mutex.isLocked) scope.launch {
            mutex.withLock {
                controller.mapWindow?.also {
                    val currentCamera = it.map.cameraPosition
                    val camera = CameraPosition(
                        currentCamera.target,
                        currentCamera.zoom + if (isPlus) 1f else -1f,
                        currentCamera.azimuth,
                        currentCamera.tilt,
                    )
                    it.map.move(camera, Animation(Animation.Type.SMOOTH, 1f), null)
                }
                delay(1.seconds)
            }
        }
    }

    Column(
        modifier = Modifier
            .width(ButtonSize)
            .shadow(
                elevation = ShadowElevation,
                shape = Shapes.Shape24,
                spotColor = Colors.ShadowPrimary,
                ambientColor = Colors.ShadowPrimary,
            )
            .clip(Shapes.Shape24)
            .background(Colors.White),
    ) {
        Button(
            onClick = { zoom(true) },
            icon = R.drawable.ic_plus,
        )
        HorizontalDivider(color = Colors.DividerPrimary)
        Button(
            onClick = { zoom(false) },
            icon = R.drawable.ic_minus,
        )
    }
}

@Composable
private fun Button(
    onClick: () -> Unit,
    icon: Int,
) {
    Box(
        modifier = Modifier
            .size(ButtonSize)
            .rippleClickable(isBounded = true) { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.size(IconSize),
            painter = painterResource(icon),
            tint = Colors.ButtonSecondaryBackground,
            contentDescription = null,
        )
    }
}