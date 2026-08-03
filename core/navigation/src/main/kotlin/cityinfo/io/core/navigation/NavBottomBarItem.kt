package cityinfo.io.core.navigation

import androidx.compose.runtime.Immutable
import kotlin.uuid.Uuid

@Immutable
data class NavBottomBarItem(
    val id: String = Uuid.random().toHexString(),
    val iconRes: Int,
    val route: Any,
)
