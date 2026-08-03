package cityinfo.io.core.base.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.FabPosition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cityinfo.io.core.base.managers.ScreenManager
import cityinfo.io.core.base.managers.rememberScreenManager
import cityinfo.io.core.uiKit.base.Colors
import cityinfo.io.core.uiKit.extensions.SystemUiColors

@Composable
internal fun BaseScreen(
    modifier: Modifier = Modifier,
    screenManager: ScreenManager = rememberScreenManager(),
    background: Color = Colors.White,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    fabButton: @Composable () -> Unit = {},
    fabPosition: FabPosition = FabPosition.End,
    isError: Boolean,
    content: @Composable (PaddingValues) -> Unit,
) {
    SystemUiColors(
        statusBarColor = if (isError) {
            screenManager.state.colorUiState.errorStatusBarColor
        } else {
            screenManager.state.colorUiState.statusBarColor
        },
        navigationBarColor = screenManager.state.colorUiState.navigationBarColor,
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .then(modifier),
        backgroundColor = Color.Transparent,
        topBar = topBar,
        bottomBar = bottomBar,
        scaffoldState = screenManager.scaffoldState,
        floatingActionButton = fabButton,
        floatingActionButtonPosition = fabPosition,
        content = content,
    )
}
