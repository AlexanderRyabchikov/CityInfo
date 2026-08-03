package cityinfo.io.feature.map.impl.data.reponses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityMapResponse(
    @SerialName("items")
    val items: List<CityMapItemResponse>? = null,
    @SerialName("count")
    val count: Int? = null,
)

@Serializable
data class CityMapItemResponse(
    @SerialName("id")
    val id: Long? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("country")
    val country: String? = null,
    @SerialName("lat")
    val latitude: Double? = null,
    @SerialName("lon")
    val longitude: Double? = null,
    @SerialName("pop")
    val population: Long? = null,
)
