package cityinfo.io.feature.map.api.repositories

import cityinfo.io.feature.map.api.models.CityMap

interface CityMapRepository {

    suspend fun getCities(centerLatitude: Double, centerLongitude: Double, radius: Double): List<CityMap>
}