package cityinfo.io.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import cityinfo.io.core.navigation.NavBottomBarItem
import cityinfo.io.core.uiKit.base.Colors
import cityinfo.io.core.uiKit.R
import cityinfo.io.feature.city.api.navigation.NavigationCity
import cityinfo.io.feature.map.api.navigation.NavigationMap

val navigationItems = listOf(
    NavBottomBarItem(
        iconRes = R.drawable.ic_cities_list,
        route = NavigationCity.CitiesList,
    ),
    NavBottomBarItem(
        iconRes = R.drawable.ic_map,
        route = NavigationMap.Map,
    ),
)

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    val matchedIndex = navigationItems.indexOfFirst { item ->
        currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true
    }

    if (matchedIndex < 0) return

    NavigationBar(
        modifier = Modifier.shadow(
            elevation = 16.dp,
            spotColor = Colors.ShadowPrimary,
            ambientColor = Colors.ShadowPrimary,
        ),
        containerColor = Colors.White,
        contentColor = Colors.IconSecondary,
        tonalElevation = 0.dp,
        windowInsets = WindowInsets.navigationBars,
    ) {
        navigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == matchedIndex,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = null,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Colors.BrandAccentColor,
                    unselectedIconColor = Colors.IconSecondary,
                    indicatorColor = Colors.BrandBackgroundColor,
                ),
            )
        }
    }
}
