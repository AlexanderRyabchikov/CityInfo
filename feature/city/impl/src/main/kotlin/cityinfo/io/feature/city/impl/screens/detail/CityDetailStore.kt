package cityinfo.io.feature.city.impl.screens.detail

import cityinfo.io.core.base.mvi.states.BaseState
import cityinfo.io.core.base.mvi.stores.BaseAppStore
import cityinfo.io.core.network.ExceptionType
import cityinfo.io.core.network.toExceptionType
import cityinfo.io.feature.city.api.models.City
import cityinfo.io.feature.city.impl.data.internal.CityDetailArgs
import cityinfo.io.feature.city.impl.interactors.CityDetailInteractor

data class CityDetailState(
    override val error: ExceptionType? = null,
    override val isLoading: Boolean = false,
    val city: City = City.EMPTY,
    val cacheId: String,
) : BaseState

sealed interface CityDetailActions {
    data object OnInit : CityDetailActions
    data object OnReload : CityDetailActions
    data object OnBack : CityDetailActions
    data object OnSearchInBrowser : CityDetailActions
}

sealed interface CityDetailEffect {
    data object NavigateBack : CityDetailEffect
    class OpenSearchInBrowser(val cityName: String) : CityDetailEffect
}

class CityDetailStore(
    args: CityDetailArgs,
    interactor: CityDetailInteractor,
) : BaseAppStore<CityDetailState, CityDetailEffect, CityDetailActions>(
    initialState = CityDetailState(
        cacheId = args.cacheId,
    ),
), CityDetailInteractor by interactor {

    init {
        dispatch(CityDetailActions.OnInit)
    }

    override fun dispatch(action: CityDetailActions) {
        when(action) {
            is CityDetailActions.OnInit -> intent {
                reduce { state.copy(error = null) }
                runCatching {
                    getCity(state.cacheId)
                }.onSuccess {
                    reduce { state.copy(city = it) }
                }.onFailure {
                    reduce { state.copy(error = it.toExceptionType()) }
                }
            }
            is CityDetailActions.OnReload -> intent {
                dispatch(CityDetailActions.OnInit)
            }
            is CityDetailActions.OnBack -> intent {
                postSideEffect(CityDetailEffect.NavigateBack)
            }
            is CityDetailActions.OnSearchInBrowser -> intent {
                postSideEffect(CityDetailEffect.OpenSearchInBrowser(state.city.name))
            }
        }
    }
}