package cityinfo.io.feature.city.impl.mappers

import cityinfo.io.core.paging.PagedData
import cityinfo.io.core.paging.core.PagedResponse
import cityinfo.io.core.utils.toCountryName
import cityinfo.io.feature.city.api.models.City
import cityinfo.io.feature.city.impl.data.responses.CityItemResponse
import cityinfo.io.feature.city.impl.data.responses.CityResponse

fun CityResponse.toData(): PagedResponse<City> {
    return PagedData(
        items = items?.map { it.toData() } ?: emptyList(),
        limit = limit ?: 0,
        page = page ?: 0,
        totalCount = totalCount ?: 0,
    )
}

private fun CityItemResponse.toData(): City {
    return City(
        id = id ?: 0,
        name = name.orEmpty(),
        country = country.toCountryName(),
        latitude = latitude ?: 0.0,
        longitude = longitude ?: 0.0,
        population = population ?: 0,
    )
}