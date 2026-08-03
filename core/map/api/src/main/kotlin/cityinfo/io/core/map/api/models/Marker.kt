package cityinfo.io.core.map.api.models

interface Marker {
    val longitude: Double
    val latitude: Double
    val data: MarkerData
}

interface MarkerData
