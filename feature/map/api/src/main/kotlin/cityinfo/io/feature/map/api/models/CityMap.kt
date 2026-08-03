package cityinfo.io.feature.map.api.models

import cityinfo.io.core.map.api.models.Marker
import cityinfo.io.core.map.api.models.MarkerData

data class CityMap(
    override val longitude: Double,
    override val latitude: Double,
    override val data: CityMapMarkerData,
) : Marker {

    companion object {
        val EMPTY = CityMap(
            longitude = 0.0,
            latitude = 0.0,
            data = CityMapMarkerData(
                id = 0,
                name = "",
                country = "",
                population = 0,
            ),
        )
    }
}

data class CityMapMarkerData(
    val id: Long,
    val name: String,
    val country: String,
    val population: Long,
) : MarkerData
