package cityinfo.io.feature.city.impl.screens.detail

import cityinfo.io.core.base.mvi.handlers.ScreenHandler

class CityDetailHandler(
    override val storeInstance: CityDetailStore? = null
) : ScreenHandler<CityDetailStore> {

    override fun onClickBack() = store.dispatch(CityDetailActions.OnBack)

    override fun onReload() = store.dispatch(CityDetailActions.OnReload)

    fun onSearchInBrowser() = store.dispatch(CityDetailActions.OnSearchInBrowser)
}