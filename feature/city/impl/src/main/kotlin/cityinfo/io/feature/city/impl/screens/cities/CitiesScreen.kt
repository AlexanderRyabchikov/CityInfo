package cityinfo.io.feature.city.impl.screens.cities

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import cityinfo.io.core.base.managers.rememberScreenManager
import cityinfo.io.core.base.mvi.compose.rememberStore
import cityinfo.io.core.base.mvi.handlers.rememberScreenHandler
import cityinfo.io.feature.city.api.navigation.NavigationCity
import cityinfo.io.feature.city.impl.screens.cities.components.CitiesContent
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun CitiesScreen(navController: NavController) {
    val store = rememberStore<CitiesStore>()
    val state by store.collectAsState()
    val handler = rememberScreenHandler(store) { CitiesHandler(store) }
    val screenManager = rememberScreenManager(
        isEnabledPullToRefresh = true
    )

    CitiesContent(
        state = state,
        handler = handler,
        screenManager = screenManager
    )

    store.collectSideEffect { effects ->
        when (effects) {
            is CitiesEffects.NavigateToCityDetails -> {
                navController.navigate(NavigationCity.CityDetails(effects.cacheId))
            }
        }
    }
}