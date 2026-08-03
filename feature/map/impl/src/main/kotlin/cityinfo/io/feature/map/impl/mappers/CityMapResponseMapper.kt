package cityinfo.io.feature.map.impl.mappers

import cityinfo.io.core.utils.toCountryName
import cityinfo.io.feature.map.api.models.CityMap
import cityinfo.io.feature.map.api.models.CityMapMarkerData
import cityinfo.io.feature.map.impl.data.reponses.CityMapItemResponse
import cityinfo.io.feature.map.impl.data.reponses.CityMapResponse

fun CityMapResponse.toData(): List<CityMap> {
    return items?.map { it.toData() } ?: emptyList()
}

private fun CityMapItemResponse.toData(): CityMap {
    return CityMap(
        latitude = latitude ?: 0.0,
        longitude = longitude ?: 0.0,
        data = CityMapMarkerData(
            id = id ?: 0,
            name = name ?: "",
            country = country?.toCountryName() ?: "",
            population = population ?: 0,
        ),
    )
}