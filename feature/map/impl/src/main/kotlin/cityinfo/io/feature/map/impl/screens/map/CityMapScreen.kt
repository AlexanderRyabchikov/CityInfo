package cityinfo.io.feature.map.impl.screens.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import cityinfo.io.core.base.managers.rememberBrowserManager
import cityinfo.io.core.base.managers.rememberScreenManager
import cityinfo.io.core.base.mvi.compose.rememberStore
import cityinfo.io.core.base.mvi.handlers.rememberScreenHandler
import cityinfo.io.feature.map.impl.screens.map.components.CityMapContent
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun CityMapScreen(navController: NavController) {

    val store = rememberStore<CityMapStore>()
    val state by store.collectAsState()
    val handler = rememberScreenHandler(store) { CityMapHandler(store) }
    val screenManager = rememberScreenManager(
        isEnabledPaddingContent = false
    )
    val browserManager = rememberBrowserManager()

    CityMapContent(
        state = state,
        handler = handler,
        screenManager = screenManager,
    )

    store.collectSideEffect { effects ->
        when(effects) {
            is CityMapEffects.OpenSearchInBrowser -> browserManager.openSearch(effects.cityName)
        }
    }
}