package cityinfo.io.core.base.mvi.states

import cityinfo.io.core.network.ExceptionType

interface BaseState {
    val isLoading: Boolean

    val error: ExceptionType?

    val isRefresh: Boolean
        get() = false
}

val BaseState.isAvailableContent
    get() = !isLoading && error == null
