package cityinfo.io.feature.city.api.repositories

import cityinfo.io.core.paging.core.PagedResponse
import cityinfo.io.feature.city.api.models.City

interface CityRepository {

    suspend fun getCities(page: Int, query: String, limit: Int): PagedResponse<City>
}