package cityinfo.io.core.mvi

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.OrbitContainer
import org.orbitmvi.orbit.OrbitContainerHost
import org.orbitmvi.orbit.viewmodel.orbitContainer

abstract class MviViewModel<STATE : Any, SIDE_EFFECT : Any, EVENT : Any>(
    initialState: STATE,
) : ViewModel(), OrbitContainerHost<STATE, STATE, SIDE_EFFECT> {

    override val container: OrbitContainer<STATE, STATE, SIDE_EFFECT> = orbitContainer(initialState)

    abstract fun dispatch(action: EVENT)

    open fun clearVM() {
        onCleared()
    }
}