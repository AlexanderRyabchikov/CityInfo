package cityinfo.io.core.base.screens.components

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cityinfo.io.core.uiKit.R
import cityinfo.io.core.uiKit.base.Colors
import cityinfo.io.core.uiKit.components.ScreenTopBarBack

@Composable
fun ScreenTopBar(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isAlignCenterMiddleContent: Boolean = true,
    backgroundColor: Color = Colors.White,
    titleColor: Color = Colors.TextPrimary,
    title: String,
    subtitle: String? = null,
    onClickBack: (() -> Unit)? = null,
    navigationColor: Color = Colors.IconPrimary,
    navigationIconRes: Int = R.drawable.ic_arrow_back,
) {
    MDTopBarComponentHandler()
    ScreenTopBarBack(
        modifier = Modifier
            .statusBarsPadding()
            .then(modifier),
        isLoading = isLoading,
        backgroundColor = backgroundColor,
        colorBackArrow = navigationColor,
        colorTitle = titleColor,
        title = title,
        subtitle = subtitle,
        onClickBack = onClickBack,
        leftIconRes = navigationIconRes,
        isAlignCenterMiddleContent = isAlignCenterMiddleContent,
    )
}

@Composable
fun ScreenEmptyTopBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent,
) {
    MDTopBarComponentHandler()
    ScreenTopBarBack(
        modifier = Modifier
            .statusBarsPadding()
            .then(modifier),
        middleContent = null,
        leftContent = null,
        rightContent = null,
        onClickBack = null,
        backgroundColor = backgroundColor,
    )
}

@Composable
private fun MDTopBarComponentHandler() {
    var components by LocalScreenComponents.current
    DisposableEffect(components) {
        components = components.copy(isActiveTopBar = true)
        onDispose {
            components = components.copy(isActiveTopBar = false)
        }
    }
}
