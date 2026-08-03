package cityinfo.io.feature.map.impl.screens.map

import cityinfo.io.core.base.mvi.states.BaseState
import cityinfo.io.core.base.mvi.stores.BaseAppStore
import cityinfo.io.core.network.ExceptionType
import cityinfo.io.core.network.toExceptionType
import cityinfo.io.feature.map.api.models.CityMap
import cityinfo.io.feature.map.impl.data.internal.CityMapCamera
import cityinfo.io.feature.map.impl.interactors.CityMapInteractor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import org.orbitmvi.orbit.syntax.Syntax

private const val CAMERA_DEBOUNCE = 500L
private const val SHEET_HIDE_DURATION = 300L

data class CityMapState(
    override val error: ExceptionType? = null,
    override val isLoading: Boolean = true,
    val isUpdating: Boolean = false,
    val cities: List<CityMap> = emptyList(),
    val selectedCity: CityMap? = null
) : BaseState

sealed interface CityMapActions {
    class OnSelectCity(val city: CityMap) : CityMapActions
    class OnCameraChanged(val camera: CityMapCamera) : CityMapActions
    data object OnInit : CityMapActions
    data object OnReload : CityMapActions
    data object OnCloseCityInfo : CityMapActions
    data object OnSearchInBrowser : CityMapActions
}

sealed interface CityMapEffects {
    class OpenSearchInBrowser(val cityName: String) : CityMapEffects
}

class CityMapStore(
    interactor: CityMapInteractor,
) : BaseAppStore<CityMapState, CityMapEffects, CityMapActions>(
    initialState = CityMapState()
), CityMapInteractor by interactor {

    private val cameraFlow = MutableStateFlow<CityMapCamera?>(null)

    init {
        dispatch(CityMapActions.OnInit)
        intent {
            cameraFlow
                .filterNotNull()
                .debounce(CAMERA_DEBOUNCE)
                .distinctUntilChanged()
                .collectLatest { camera -> updateCities(camera) }
        }
    }

    override fun dispatch(action: CityMapActions) {
        when (action) {
            is CityMapActions.OnInit -> intent {
                reduce { state.copy(isLoading = true, error = null) }
                runCatching {
                    loadCities(CityMapCamera.DEFAULT)
                }.onSuccess { cities ->
                    reduce { state.copy(cities = cities, isLoading = false) }
                }.onFailure { error ->
                    reduce { state.copy(error = error.toExceptionType(), isLoading = false) }
                }
            }
            is CityMapActions.OnReload -> intent {
                dispatch(CityMapActions.OnInit)
            }
            is CityMapActions.OnCameraChanged -> {
                cameraFlow.value = action.camera
            }
            is CityMapActions.OnSelectCity -> intent {
                val isSameCity = state.selectedCity?.data?.id == action.city.data.id

                if (state.selectedCity != null) {
                    reduce { state.copy(selectedCity = null) }
                    if (isSameCity) return@intent
                    delay(SHEET_HIDE_DURATION)
                }

                reduce { state.copy(selectedCity = action.city) }
            }
            is CityMapActions.OnCloseCityInfo -> intent {
                reduce { state.copy(selectedCity = null) }
            }
            is CityMapActions.OnSearchInBrowser -> intent {
                postSideEffect(CityMapEffects.OpenSearchInBrowser(state.selectedCity?.data?.name.orEmpty()))
            }
        }
    }

    private suspend fun Syntax<CityMapState, CityMapEffects>.updateCities(camera: CityMapCamera) {
        reduce { state.copy(isUpdating = true) }
        runCatching {
            loadCities(camera)
        }.onSuccess { cities ->
            reduce { state.copy(cities = cities, isUpdating = false) }
        }.onFailure { error ->
            reduce {
                state.copy(
                    isUpdating = false,
                    error = error.toExceptionType().takeIf { state.cities.isEmpty() },
                )
            }
        }
    }

    private suspend fun loadCities(camera: CityMapCamera): List<CityMap> = loadCities(
        centerLatitude = camera.latitude,
        centerLongitude = camera.longitude,
        radius = camera.radius,
    )
}