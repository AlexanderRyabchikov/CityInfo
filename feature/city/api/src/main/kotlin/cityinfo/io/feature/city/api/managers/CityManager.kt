package cityinfo.io.feature.city.api.managers

import cityinfo.io.feature.city.api.models.City

interface CityManager {

   fun saveCity(city: City): String

   fun getCity(id: String): City
}