package cityinfo.io.feature.city.impl.screens.cities

import cityinfo.io.core.base.mvi.states.BaseState
import cityinfo.io.core.base.mvi.stores.BasePagingStore
import cityinfo.io.core.network.ExceptionType
import cityinfo.io.core.paging.PagedData
import cityinfo.io.core.paging.core.PagingDataFlow
import cityinfo.io.core.paging.core.PagingState
import cityinfo.io.core.paging.data
import cityinfo.io.feature.city.api.models.City
import cityinfo.io.feature.city.impl.interactors.CityListInteractor

data class CitiesState(
    override val error: ExceptionType? = null,
    override val isLoading: Boolean = false,
    override val isRefresh: Boolean = false,
    override val pageFlow: PagingDataFlow<City>? = null,
    val query: String = "",
) : BaseState, PagingState<City>

sealed interface CitiesActions {
    data object OnRefresh : CitiesActions
    data object OnReload : CitiesActions
    class OnSearch(val query: String) : CitiesActions
    class OnCityClick(val city: City) : CitiesActions
}

sealed interface CitiesEffects {
    class NavigateToCityDetails(val cacheId: String) : CitiesEffects
}

class CitiesStore(
    interactor: CityListInteractor,
) : BasePagingStore<CitiesState, CitiesEffects, CitiesActions, City>(
    initialState = CitiesState(),
    isSkipCancellationException = true,
), CityListInteractor by interactor {

    override fun dispatch(action: CitiesActions) {
        when (action) {
            is CitiesActions.OnRefresh -> intent {
                reduce { state.copy(isRefresh = true) }
                pagingData.reload()
            }
            is CitiesActions.OnReload -> intent {
                reduce { state.copy(error = null) }
                pagingData.reload()
            }
            is CitiesActions.OnSearch -> intent {
                if (action.query.trim() != state.query) {
                    reduce { state.copy(query = action.query) }
                    pagingData.reload()
                }
            }
            is CitiesActions.OnCityClick -> intent {
                val cacheId = saveCity(action.city)
                postSideEffect(CitiesEffects.NavigateToCityDetails(cacheId))
            }
        }
    }

    override fun onInit(flow: PagingDataFlow<City>) {
        intent {
            reduce { state.copy(pageFlow = flow) }
        }
    }

    override fun onLoadFirstPage() {
        intent {
            reduce { state.copy(isRefresh = false) }
        }
    }

    override fun onPagingError(error: ExceptionType?) {
        intent {
            reduce {
                state.copy(
                    error = error,
                    isRefresh = false,
                )
            }
        }
    }

    override suspend fun onLoadPage(page: Int?, state: CitiesState): PagedData<City> {
        val response = loadCities(
            page = page ?: 1,
            query = state.query,
        )
        return response.data
    }
}