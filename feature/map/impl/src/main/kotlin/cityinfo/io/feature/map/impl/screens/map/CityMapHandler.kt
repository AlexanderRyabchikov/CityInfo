package cityinfo.io.feature.map.impl.screens.map

import cityinfo.io.core.base.mvi.handlers.ScreenHandler
import cityinfo.io.feature.map.api.models.CityMap
import cityinfo.io.feature.map.impl.data.internal.CityMapCamera

class CityMapHandler(
    override val storeInstance: CityMapStore? = null
) : ScreenHandler<CityMapStore> {

    override fun onClickBack() = Unit

    override fun onReload() = store.dispatch(CityMapActions.OnReload)

    fun onSelectCity(city: CityMap) = store.dispatch(CityMapActions.OnSelectCity(city))

    fun onCloseCityInfo() = store.dispatch(CityMapActions.OnCloseCityInfo)

    fun onSearchInBrowser() = store.dispatch(CityMapActions.OnSearchInBrowser)

    fun onCameraChanged(latitude: Double, longitude: Double, radius: Double) = store.dispatch(
        CityMapActions.OnCameraChanged(
            CityMapCamera(
                latitude = latitude,
                longitude = longitude,
                radius = radius,
            )
        )
    )
}