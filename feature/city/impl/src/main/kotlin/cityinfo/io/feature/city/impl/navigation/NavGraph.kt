package cityinfo.io.feature.city.impl.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import cityinfo.io.feature.city.api.navigation.NavigationCity
import cityinfo.io.feature.city.impl.data.internal.CityDetailArgs
import cityinfo.io.feature.city.impl.screens.cities.CitiesScreen
import cityinfo.io.feature.city.impl.screens.detail.CityDetailScreen

fun NavGraphBuilder.cityInfoNavGraph(navController: NavController) {
    composable<NavigationCity.CitiesList> {
        CitiesScreen(navController)
    }

    composable<NavigationCity.CityDetails> {
        val data = it.toRoute<NavigationCity.CityDetails>()
        val args = CityDetailArgs(
            cacheId = data.id
        )
        CityDetailScreen(args, navController)
    }
}