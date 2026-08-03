package cityinfo.io.feature.map.impl.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import cityinfo.io.feature.map.api.navigation.NavigationMap
import cityinfo.io.feature.map.impl.screens.map.CityMapScreen

fun NavGraphBuilder.mapNavGraph(navController: NavController) {
    composable<NavigationMap.Map> {
        CityMapScreen(navController)
    }
}