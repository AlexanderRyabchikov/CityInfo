package cityinfo.io.core.uiKit.bottomsheets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import cityinfo.io.core.uiKit.base.Colors
import cityinfo.io.core.uiKit.base.Shapes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberBottomSheet(
    skipPartiallyExpanded: Boolean = false,
): CustomSheetState {
    val isAvailableClose by LocalAvailableCloseBottomSheet.current
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded,
        confirmValueChange = { isAvailableClose },
    )
    return remember(key1 = sheetState, key2 = scope) { CustomSheetState(sheetState = sheetState, scope = scope) }
}

class CustomSheetState @OptIn(ExperimentalMaterial3Api::class)
internal constructor(
    internal val sheetState: SheetState,
    private val scope: CoroutineScope,
) {

    @OptIn(ExperimentalMaterial3Api::class)
    val isVisible = sheetState.hasPartiallyExpandedState

    @OptIn(ExperimentalMaterial3Api::class)
    fun hide() {
        scope.launch { sheetState.hide() }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun show() {
        scope.launch { sheetState.show() }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun expand() {
        scope.launch { sheetState.expand() }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    fun partialExpand() {
        scope.launch { sheetState.partialExpand() }
    }
}

val LocalAvailableCloseBottomSheet = compositionLocalOf { mutableStateOf(true) }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    modifier: Modifier = Modifier,
    sheetState: CustomSheetState,
    shape: Shape = Shapes.Shape24,
    signShape: Shape = CircleShape,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onDismissRequest: () -> Unit = {},
    containerColor: Color = Colors.White,
    dragHandle: @Composable (() -> Unit) = { DefaultDragHandler(signShape) },
    content: @Composable ColumnScope.() -> Unit,
) {
    if (sheetState.sheetState.isVisible)
        ModalBottomSheet(
            modifier = modifier,
            sheetState = sheetState.sheetState,
            onDismissRequest = onDismissRequest,
            shape = shape,
            containerColor = containerColor,
            dragHandle = dragHandle,
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(contentPadding),
                ) {
                    content()
                }
            },
        )
}

@Composable
private fun DefaultDragHandler(signShape: Shape = CircleShape) = Spacer(
    modifier = Modifier
        .padding(top = 8.dp)
        .size(width = 48.dp, height = 4.dp)
        .background(color = Colors.DraggerColor, shape = signShape),
)
