package cityinfo.io.feature.city.impl.screens.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import cityinfo.io.core.base.managers.rememberBrowserManager
import cityinfo.io.core.base.managers.rememberScreenManager
import cityinfo.io.core.base.mvi.compose.rememberStore
import cityinfo.io.core.base.mvi.handlers.rememberScreenHandler
import cityinfo.io.feature.city.impl.data.internal.CityDetailArgs
import cityinfo.io.feature.city.impl.screens.detail.components.CityDetailContent
import org.koin.core.parameter.parametersOf
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun CityDetailScreen(
    args: CityDetailArgs,
    navController: NavController,
) {
    val store = rememberStore<CityDetailStore>(
        parameters = parametersOf(args)
    )
    val state by store.collectAsState()
    val handler = rememberScreenHandler(store) { CityDetailHandler(store) }
    val screenManager = rememberScreenManager()
    val browserManager = rememberBrowserManager()

    CityDetailContent(
        state = state,
        handler = handler,
        screenManager = screenManager
    )

    store.collectSideEffect { effect ->
        when(effect) {
            is CityDetailEffect.NavigateBack -> navController.popBackStack()
            is CityDetailEffect.OpenSearchInBrowser -> browserManager.openSearch(effect.cityName)
        }
    }
}