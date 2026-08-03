package cityinfo.io.feature.city.impl.screens.cities

import cityinfo.io.core.base.mvi.handlers.ScreenHandler
import cityinfo.io.feature.city.api.models.City

class CitiesHandler(
    override val storeInstance: CitiesStore? = null
) : ScreenHandler<CitiesStore> {

    override fun onClickBack() = Unit

    override fun onReload() = store.dispatch(CitiesActions.OnReload)

    override fun onPullRefresh() = store.dispatch(CitiesActions.OnRefresh)

    fun onSearch(query: String) = store.dispatch(CitiesActions.OnSearch(query))

    fun onCityClick(city: City) = store.dispatch(CitiesActions.OnCityClick(city))
}