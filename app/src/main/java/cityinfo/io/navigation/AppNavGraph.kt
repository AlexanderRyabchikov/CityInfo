package cityinfo.io.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import cityinfo.io.feature.city.api.navigation.NavigationCity
import cityinfo.io.feature.city.impl.navigation.cityInfoNavGraph
import cityinfo.io.feature.map.impl.navigation.mapNavGraph

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = NavigationCity.CitiesList,
    ) {
        cityInfoNavGraph(navController)
        mapNavGraph(navController)
    }
}