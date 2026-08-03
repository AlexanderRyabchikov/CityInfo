package cityinfo.io.feature.city.api.usecases

import cityinfo.io.core.paging.core.PagedResponse
import cityinfo.io.feature.city.api.models.City

interface GetCityListUseCase {

    suspend operator fun invoke(page: Int, query: String, limit: Int): PagedResponse<City>
}