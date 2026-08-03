package cityinfo.io.core.base.screens.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cityinfo.io.core.uiKit.base.Colors
import cityinfo.io.core.uiKit.extensions.bottomShadow
import cityinfo.io.core.uiKit.extensions.required

@Composable
fun ScreenBottomBar(
    isVisible: Boolean,
    isShowShadow: Boolean = true,
    isNeedImePaddings: Boolean = true,
    backgroundColor: Color = Colors.White,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit,
) {
    var components by LocalScreenComponents.current
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + slideInVertically(
            animationSpec = tween(250),
            initialOffsetY = { fullHeight -> fullHeight }, // снизу вверх
        ),
        exit = fadeOut() + slideOutVertically(
            animationSpec = tween(250),
            targetOffsetY = { fullHeight -> fullHeight }, // вверх вниз
        ),
    ) {
        DisposableEffect(key1 = backgroundColor, key2 = components) {
            components = components.copy(isActiveBottomBar = true)
            onDispose {
                components = components.copy(isActiveBottomBar = false)
            }
        }

        Column(
            modifier = Modifier
                .required(isShowShadow) { bottomShadow() }
                .fillMaxWidth()
                .background(backgroundColor)
                .navigationBarsPadding()
                .required(isNeedImePaddings) { imePadding() }
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .padding(bottom = 12.dp),
            content = content,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
        )
    }
}
