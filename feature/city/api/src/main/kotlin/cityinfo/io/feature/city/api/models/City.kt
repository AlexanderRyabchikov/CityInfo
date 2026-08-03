package cityinfo.io.feature.city.api.models

import cityinfo.io.core.paging.core.PagingItem
import kotlin.uuid.Uuid

data class City(
    override val pagingId: String = Uuid.random().toHexString(),
    val id: Long,
    val name: String,
    val country: String,
    val longitude: Double,
    val latitude: Double,
    val population: Long,
) : PagingItem {

    companion object {
        val EMPTY = City(
            id = 0,
            name = "",
            country = "",
            longitude = 0.0,
            latitude = 0.0,
            population = 0,
        )
    }
}
