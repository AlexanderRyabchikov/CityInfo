package cityinfo.io.feature.map.api.usecases

import cityinfo.io.feature.map.api.models.CityMap

interface GetCityMapUseCase {

    suspend operator fun invoke(centerLatitude: Double, centerLongitude: Double, radius: Double): List<CityMap>
}