package cityinfo.io.feature.map.impl.interactors

import cityinfo.io.feature.map.api.models.CityMap
import cityinfo.io.feature.map.api.usecases.GetCityMapUseCase

interface CityMapInteractor {

    suspend fun loadCities(centerLatitude: Double, centerLongitude: Double, radius: Double): List<CityMap>
}

internal class CityMapInteractorImpl(
    private val getCityMapUseCase: GetCityMapUseCase
) : CityMapInteractor {

    override suspend fun loadCities(
        centerLatitude: Double,
        centerLongitude: Double,
        radius: Double
    ): List<CityMap> {
        return getCityMapUseCase(
            centerLatitude = centerLatitude,
            centerLongitude = centerLongitude,
            radius = radius
        )
    }
}