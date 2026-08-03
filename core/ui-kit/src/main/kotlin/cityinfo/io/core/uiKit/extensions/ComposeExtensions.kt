package cityinfo.io.core.uiKit.extensions

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.LocalActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import cityinfo.io.core.uiKit.base.Colors

@Composable
fun SystemUiColors(
    statusBarColor: Color = Colors.White,
    navigationBarColor: Color = Colors.White,
) {

    val activity = LocalActivity.current as? ComponentActivity
    SideEffect {
        activity?.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                statusBarColor.toArgb(),
                statusBarColor.toArgb()
            ),
            navigationBarStyle = SystemBarStyle.auto(
                navigationBarColor.toArgb(),
                navigationBarColor.toArgb()
            ),
        )
    }
}