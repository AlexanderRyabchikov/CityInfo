package cityinfo.io.feature.city.impl.interactors

import cityinfo.io.core.paging.core.PagedResponse
import cityinfo.io.feature.city.api.managers.CityManager
import cityinfo.io.feature.city.api.models.City
import cityinfo.io.feature.city.api.usecases.GetCityListUseCase

interface CityListInteractor {
    suspend fun loadCities(page: Int, query: String, limit: Int = 20): PagedResponse<City>

    fun saveCity(city: City): String
}

internal class CityListInteractorImpl(
    private val getCitiesUseCase: GetCityListUseCase,
    private val cityManager: CityManager,
) : CityListInteractor {

    override suspend fun loadCities(page: Int, query: String, limit: Int, ): PagedResponse<City> {
        return getCitiesUseCase(page = page, query = query, limit = limit)
    }

    override fun saveCity(city: City): String {
        return cityManager.saveCity(city)
    }
}