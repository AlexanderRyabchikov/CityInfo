package cityinfo.io.feature.city.impl.interactors

import cityinfo.io.feature.city.api.managers.CityManager
import cityinfo.io.feature.city.api.models.City

interface CityDetailInteractor {

    fun getCity(cacheId: String): City
}

internal class CityDetailInteractorImpl(
    private val cityManager: CityManager,
) : CityDetailInteractor {

    override fun getCity(cacheId: String): City {
        return cityManager.getCity(cacheId)
    }
}