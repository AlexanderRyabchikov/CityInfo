package cityinfo.io.feature.map.api.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavigationMap {

    @Serializable
    data object Map: NavigationMap
}