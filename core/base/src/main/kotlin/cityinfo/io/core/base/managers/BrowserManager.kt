package cityinfo.io.core.base.managers

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import cityinfo.io.core.uiKit.base.Colors

@Composable
fun rememberBrowserManager(
    toolbarColor: Color = Colors.White,
): BrowserManager {
    val context = LocalContext.current
    return remember(key1 = context, key2 = toolbarColor) {
        BrowserManager(
            context = context,
            toolbarColor = toolbarColor,
        )
    }
}

class BrowserManager internal constructor(
    private val context: Context,
    private val toolbarColor: Color,
) {

    fun openSearch(query: String) {
        if (query.isBlank()) return
        openUrl(SEARCH_URL + Uri.encode(query))
    }

    fun openUrl(url: String) {
        val uri = Uri.parse(url)
        runCatching {
            CustomTabsIntent.Builder()
                .setShowTitle(true)
                .setUrlBarHidingEnabled(true)
                .setDefaultColorSchemeParams(
                    CustomTabColorSchemeParams.Builder()
                        .setToolbarColor(toolbarColor.toArgb())
                        .build()
                )
                .build()
                .launchUrl(context, uri)
        }.onFailure {
            runCatching {
                context.startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }
    }

    private companion object {
        const val SEARCH_URL = "https://www.google.com/search?q="
    }
}