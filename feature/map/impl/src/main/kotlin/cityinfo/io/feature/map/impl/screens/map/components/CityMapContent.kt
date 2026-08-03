package cityinfo.io.feature.map.impl.screens.map.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cityinfo.io.core.base.managers.ScreenManager
import cityinfo.io.core.base.screens.Screen
import cityinfo.io.core.uiKit.base.Colors
import cityinfo.io.feature.map.impl.screens.map.CityMapHandler
import cityinfo.io.feature.map.impl.screens.map.CityMapState
import cityinfo.io.core.map.widgets.components.YandexMap
import cityinfo.io.core.map.widgets.components.ZoomButtons
import cityinfo.io.core.map.widgets.controller.rememberYandexMapController

@Composable
internal fun CityMapContent(
    state: CityMapState,
    handler: CityMapHandler,
    screenManager: ScreenManager
) {
    val controller = rememberYandexMapController()

    Screen(
        modifier = Modifier.consumeWindowInsets(WindowInsets.statusBars),
        state = state,
        handler = handler,
        screenManager = screenManager,
        topBar = {},
        content = {
            Box(modifier = Modifier.fillMaxSize()) {

                YandexMap(
                    modifier = Modifier.fillMaxSize(),
                    controller = controller,
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.CenterEnd,
                    content = { ZoomButtons(controller) },
                )

                MapCameraEffect(
                    controller = controller,
                    handler = handler,
                )

                CityMapMarkers(
                    controller = controller,
                    state = state,
                    handler = handler,
                )

                UpdatingIndicator(isVisible = state.isUpdating)

                CityInfoSheet(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    city = state.selectedCity?.data,
                    handler = handler,
                )
            }
        }
    )
}

@Composable
private fun BoxScope.UpdatingIndicator(isVisible: Boolean) {
    AnimatedVisibility(
        modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(top = 16.dp),
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(Colors.White, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = Colors.BrandAccentColor,
                strokeWidth = 2.dp,
            )
        }
    }
}