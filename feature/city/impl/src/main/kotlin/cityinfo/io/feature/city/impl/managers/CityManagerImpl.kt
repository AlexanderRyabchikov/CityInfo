package cityinfo.io.feature.city.impl.managers

import cityinfo.io.core.cache.cacheOf
import cityinfo.io.feature.city.api.managers.CityManager
import cityinfo.io.feature.city.api.models.City
import kotlin.time.Duration.Companion.minutes
import kotlin.uuid.Uuid

class CityManagerImpl : CityManager {

    private val cacheCity = cacheOf<String, City>(
        expireAfterWrite = 30.minutes,
        isSystem = true,
        name = "Деталка.Город.Список Городов"
    )

    override fun getCity(id: String): City {
        return requireNotNull(cacheCity.get(id))
    }

    override fun saveCity(city: City): String {
        val id = Uuid.random().toHexString()
        cacheCity.put(id, city)
        return id
    }
}