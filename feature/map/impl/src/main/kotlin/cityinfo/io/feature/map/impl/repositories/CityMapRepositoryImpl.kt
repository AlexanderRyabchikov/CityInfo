package cityinfo.io.feature.map.impl.repositories

import cityinfo.io.feature.map.api.models.CityMap
import cityinfo.io.feature.map.api.repositories.CityMapRepository
import cityinfo.io.feature.map.impl.data.reponses.CityMapResponse
import cityinfo.io.feature.map.impl.mappers.toData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val CITIES_LIMIT = 100

class CityMapRepositoryImpl(
    private val httpClient: HttpClient,
) : CityMapRepository {

    override suspend fun getCities(
        centerLatitude: Double,
        centerLongitude: Double,
        radius: Double
    ): List<CityMap> {
        return httpClient.get("/api/cities/map") {
            parameter("centerLat", centerLatitude)
            parameter("centerLng", centerLongitude)
            parameter("radius", radius)
            parameter("limit", CITIES_LIMIT)
        }.body<CityMapResponse>().toData()
    }
}