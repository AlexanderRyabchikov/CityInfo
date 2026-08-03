package cityinfo.io.init

import android.annotation.SuppressLint
import android.graphics.Color.TRANSPARENT
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.LocalActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import cityinfo.io.core.navigation.LocalNavController
import cityinfo.io.navigation.AppNavGraph

@Composable
fun AppMainContent() {
    val navController = LocalNavController.current
    AppNavGraph(navController = navController)
    EnableEdgeToEdge()
}

@SuppressLint("ContextCastToActivity")
@Composable
private fun EnableEdgeToEdge() {
    val activity = LocalActivity.current as? ComponentActivity
    SideEffect {
        activity?.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(TRANSPARENT, TRANSPARENT),
            navigationBarStyle = SystemBarStyle.auto(TRANSPARENT, TRANSPARENT),
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            activity?.window?.isNavigationBarContrastEnforced = false
        }
    }
}