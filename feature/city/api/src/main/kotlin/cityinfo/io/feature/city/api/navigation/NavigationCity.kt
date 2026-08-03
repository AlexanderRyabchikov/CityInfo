package cityinfo.io.feature.city.api.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavigationCity {

    @Serializable
    data object CitiesList: NavigationCity

    @Serializable
    data class CityDetails(val id: String): NavigationCity
}