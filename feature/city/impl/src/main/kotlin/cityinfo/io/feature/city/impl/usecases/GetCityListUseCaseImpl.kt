package cityinfo.io.feature.city.impl.usecases

import cityinfo.io.core.paging.core.PagedResponse
import cityinfo.io.feature.city.api.models.City
import cityinfo.io.feature.city.api.repositories.CityRepository
import cityinfo.io.feature.city.api.usecases.GetCityListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCityListUseCaseImpl(
    private val repository: CityRepository
) : GetCityListUseCase {

    override suspend fun invoke(page: Int, query: String, limit: Int, ): PagedResponse<City> {
        return withContext(Dispatchers.IO) {
            repository.getCities(page = page, query = query, limit = limit)
        }
    }
}