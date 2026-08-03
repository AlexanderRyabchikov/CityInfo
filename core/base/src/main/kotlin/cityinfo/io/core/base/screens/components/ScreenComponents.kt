package cityinfo.io.core.base.screens.components

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

val LocalScreenComponents = compositionLocalOf {
    mutableStateOf(MDScreenComponents())
}

data class MDScreenComponents(
    val isActiveBottomBar: Boolean = false,
    val isActiveFab: Boolean = false,
    val isActiveTopBar: Boolean = false,
)
