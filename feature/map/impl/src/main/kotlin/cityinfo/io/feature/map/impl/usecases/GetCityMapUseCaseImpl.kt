package cityinfo.io.feature.map.impl.usecases

import cityinfo.io.feature.map.api.models.CityMap
import cityinfo.io.feature.map.api.repositories.CityMapRepository
import cityinfo.io.feature.map.api.usecases.GetCityMapUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCityMapUseCaseImpl(
    private val repository: CityMapRepository
) : GetCityMapUseCase {

    override suspend fun invoke(
        centerLatitude: Double,
        centerLongitude: Double,
        radius: Double
    ): List<CityMap> {
        return withContext(Dispatchers.IO) {
            repository.getCities(
                centerLatitude = centerLatitude,
                centerLongitude = centerLongitude,
                radius = radius
            )
        }
    }
}