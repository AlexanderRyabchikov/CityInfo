package cityinfo.io.feature.city.impl.data.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityResponse(
    @SerialName("items")
    val items: List<CityItemResponse>? = null,
    @SerialName("limit")
    val limit: Int? = null,
    @SerialName("page")
    val page: Int? = null,
    @SerialName("total")
    val totalCount: Int? = null,
)

@Serializable
data class CityItemResponse(
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
