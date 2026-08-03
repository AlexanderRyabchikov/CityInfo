package cityinfo.io.core.base.mvi.stores

import cityinfo.io.core.mvi.MviViewModel
import org.koin.core.component.KoinComponent

abstract class BaseAppStore<STATE : Any, SIDE_EFFECT : Any, EVENT : Any>(
    initialState: STATE,
) : KoinComponent, MviViewModel<STATE, SIDE_EFFECT, EVENT>(
    initialState = initialState,
)
