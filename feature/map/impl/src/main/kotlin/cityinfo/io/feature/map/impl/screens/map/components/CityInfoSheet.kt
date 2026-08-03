package cityinfo.io.feature.map.impl.screens.map.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import cityinfo.io.core.uiKit.base.Colors
import cityinfo.io.feature.map.api.models.CityMapMarkerData
import cityinfo.io.feature.map.impl.screens.map.CityMapHandler
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

private val SheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
private const val DISMISS_HEIGHT_FRACTION = 0.3f
private const val DISMISS_VELOCITY = 1000f

@Composable
internal fun CityInfoSheet(
    city: CityMapMarkerData?,
    handler: CityMapHandler,
    modifier: Modifier = Modifier,
) {
    var lastCity by remember { mutableStateOf<CityMapMarkerData?>(null) }
    var sheetHeight by remember { mutableIntStateOf(0) }

    val scope = rememberCoroutineScope()
    val dragOffset = remember { Animatable(0f) }

    LaunchedEffect(city) {
        if (city != null) {
            lastCity = city
            dragOffset.snapTo(0f)
        }
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = city != null,
        enter = slideInVertically { height -> height } + fadeIn(),
        exit = slideOutVertically { height -> height } + fadeOut(),
    ) {
        Column(
            modifier = Modifier
                .offset { IntOffset(x = 0, y = dragOffset.value.roundToInt()) }
                .fillMaxWidth()
                .clip(SheetShape)
                .background(Colors.White)
                .onSizeChanged { sheetHeight = it.height }
                .consumeGestures()
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        scope.launch {
                            dragOffset.snapTo((dragOffset.value + delta).coerceAtLeast(0f))
                        }
                    },
                    onDragStopped = { velocity ->
                        val isDismissed = velocity > DISMISS_VELOCITY ||
                            dragOffset.value > sheetHeight * DISMISS_HEIGHT_FRACTION

                        if (isDismissed) handler.onCloseCityInfo() else dragOffset.animateTo(0f)
                    },
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(width = 48.dp, height = 4.dp)
                    .background(Colors.DraggerColor, CircleShape),
            )
            lastCity?.let { CityDetailContent(cityMap = it, handler = handler) }
        }
    }
}

private fun Modifier.consumeGestures() = pointerInput(Unit) {
    awaitPointerEventScope {
        while (true) {
            awaitPointerEvent().changes.forEach { it.consume() }
        }
    }
}