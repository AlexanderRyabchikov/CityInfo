package cityinfo.io.core.base.managers

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ScaffoldState
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import cityinfo.io.core.base.mvi.states.BaseState
import cityinfo.io.core.base.mvi.states.isAvailableContent
import cityinfo.io.core.network.isError
import cityinfo.io.core.uiKit.base.Colors
import kotlinx.coroutines.CoroutineScope

interface SystemUiColorState {
    val darkIconsStatusBar: Boolean
    val darkIconsNavbar: Boolean
    val statusBarColor: Color
    val navigationBarColor: Color
    val errorStatusBarColor: Color
}

data class SystemUiColorStateDefault(
    override val darkIconsNavbar: Boolean = true,
    override val darkIconsStatusBar: Boolean = false,
    override val navigationBarColor: Color = Colors.White,
    override val statusBarColor: Color = Colors.White,
    override val errorStatusBarColor: Color = Colors.White,
) : SystemUiColorState

@Composable
fun rememberScreenManager(
    isEnabledPaddingContent: Boolean = true,
    isShowComponentsIfLoading: Boolean = false,
    isShowComponentsIfError: Boolean = false,
    isEnabledPullToRefresh: Boolean = false,
    colorUiState: SystemUiColorState = SystemUiColorStateDefault(),
): ScreenManager {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val state = remember(
        isEnabledPaddingContent,
        isShowComponentsIfLoading,
        isShowComponentsIfError,
        isEnabledPullToRefresh,
        colorUiState,
    ) {
        ScreenManagerState(
            isEnabledPaddingContent = isEnabledPaddingContent,
            isShowComponentsIfLoading = isShowComponentsIfLoading,
            isShowComponentsIfError = isShowComponentsIfError,
            isEnabledPullToRefresh = isEnabledPullToRefresh,
            colorUiState = colorUiState,
        )
    }
    return remember(
        key1 = scope,
        key2 = scaffoldState,
        key3 = state,
    ) {
        ScreenManager(
            scope = scope,
            scaffoldState = scaffoldState,
            state = state,
        )
    }
}

class ScreenManagerState internal constructor(
    val isEnabledPaddingContent: Boolean,
    val isShowComponentsIfLoading: Boolean,
    val isShowComponentsIfError: Boolean,
    val isEnabledPullToRefresh: Boolean,
    val colorUiState: SystemUiColorState,
)

class ScreenManager internal constructor(
    private val scope: CoroutineScope,
    internal val scaffoldState: ScaffoldState,
    internal val state: ScreenManagerState,
)

@Composable
fun ScreenManager.hasShowBars(state: BaseState): State<Boolean> {
    return remember(key1 = state, key2 = this.state) {
        derivedStateOf {
            state.isLoading && this.state.isShowComponentsIfLoading ||
                state.error.isError && this.state.isShowComponentsIfError ||
                state.isAvailableContent
        }
    }
}
