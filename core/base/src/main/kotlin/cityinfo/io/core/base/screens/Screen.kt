package cityinfo.io.core.base.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.FabPosition
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cityinfo.io.core.base.managers.ScreenManager
import cityinfo.io.core.base.managers.hasShowBars
import cityinfo.io.core.base.managers.rememberScreenManager
import cityinfo.io.core.base.mvi.handlers.BaseScreenHandler
import cityinfo.io.core.base.mvi.states.BaseState
import cityinfo.io.core.base.mvi.states.isAvailableContent
import cityinfo.io.core.base.screens.components.LocalScreenComponents
import cityinfo.io.core.base.screens.components.ScreenEmptyTopBar
import cityinfo.io.core.network.isError
import cityinfo.io.core.uiKit.base.Colors
import cityinfo.io.core.uiKit.components.BaseLoadingUi
import cityinfo.io.core.uiKit.components.SwipeRefresh
import cityinfo.io.core.uiKit.errors.ErrorContent
import cityinfo.io.core.uiKit.extensions.required

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(
    modifier: Modifier = Modifier,
    state: BaseState,
    handler: BaseScreenHandler,
    screenManager: ScreenManager = rememberScreenManager(),
    background: Color = Colors.White,
    topBar: @Composable () -> Unit = { ScreenEmptyTopBar() },
    bottomBar: @Composable () -> Unit = {},
    fabButton: @Composable () -> Unit = {},
    fabPosition: FabPosition = FabPosition.End,
    loadingContent: @Composable () -> Unit = { BaseLoadingUi() },
    content: @Composable (PaddingValues) -> Unit,
) {
    val components by LocalScreenComponents.current
    val isShowComponents by screenManager.hasShowBars(state)

    BaseScreen(
        modifier = modifier
            .required(!components.isActiveTopBar) { statusBarsPadding() },
        background = background,
        topBar = topBar,
        screenManager = screenManager,
        bottomBar = { if (isShowComponents) bottomBar() },
        fabButton = { if (isShowComponents) fabButton() },
        fabPosition = fabPosition,
        isError = state.error.isError,
    ) { paddings ->
        SwipeRefresh(
            isRefresh = state.isRefresh,
            onRefresh = handler::onPullRefresh,
            isRefreshingEnable = state.isAvailableContent && screenManager.state.isEnabledPullToRefresh,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .required(screenManager.state.isEnabledPaddingContent) {
                        padding(paddings)
                    }
                    .required(screenManager.state.isEnabledPaddingContent && !components.isActiveBottomBar) {
                        navigationBarsPadding()
                    },
            ) {
                when {
                    state.error.isError -> ErrorContent(
                        error = state.error,
                        onRetry = handler::onReload,
                    )

                    state.isLoading -> loadingContent()

                    else -> content(paddings)
                }
            }
        }
    }
}
