package cityinfo.io.feature.map.impl.data.internal

data class CityMapCamera(
    val latitude: Double,
    val longitude: Double,
    val radius: Double,
) {

    companion object {
        const val DEFAULT_ZOOM = 9f

        val DEFAULT = CityMapCamera(
            latitude = 55.751244,
            longitude = 37.618423,
            radius = 50_000.0,
        )
    }
}