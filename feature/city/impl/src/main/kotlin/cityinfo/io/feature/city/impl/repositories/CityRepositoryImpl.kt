package cityinfo.io.feature.city.impl.repositories

import cityinfo.io.core.paging.core.PagedResponse
import cityinfo.io.feature.city.api.models.City
import cityinfo.io.feature.city.api.repositories.CityRepository
import cityinfo.io.feature.city.impl.data.responses.CityResponse
import cityinfo.io.feature.city.impl.mappers.toData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class CityRepositoryImpl(
    private val httpClient: HttpClient,
) : CityRepository {

    override suspend fun getCities(page: Int, query: String, limit: Int): PagedResponse<City> {
        return httpClient.get("/api/cities") {
            parameter("page", page)
            parameter("limit", limit)
            parameter("query", query.takeIf { it.isNotBlank() })
        }.body<CityResponse>().toData()
    }
}