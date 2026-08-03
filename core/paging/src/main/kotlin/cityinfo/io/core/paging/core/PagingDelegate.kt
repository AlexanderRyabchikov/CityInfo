@file:Suppress("UNCHECKED_CAST", "unused")

package cityinfo.io.core.paging.core

import androidx.lifecycle.viewModelScope
import cityinfo.io.core.mvi.MviViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.coroutines.cancellation.CancellationException
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

data class PagingData<T : Any>(
    val pageFLow: PagingDataFlow<T>,
    private val source: PagingSource<T>,
    private val flowUpdate: MutableSharedFlow<Any?>? = null,
) {
    fun reload() {
        source.invalidate()
    }
}

internal class PagingDelegate<T : Any>(
    scope: CoroutineScope,
    pageSize: Int = 10,
    private val isRefreshFromFirstPage: () -> Boolean = { false },
    private val onUpdate: ((T) -> T)? = null,
    private val pagedResponse: suspend (Int) -> PagedResponse<T>,
) : ReadOnlyProperty<Any, PagingData<T>> {

    private fun createSource(): PagingSource<T> = PagingSource(
        isRefreshFromFirstPage = isRefreshFromFirstPage,
        pagedResponse = { page ->
            pagedResponse(page).also {
                realItemsCount.value = it.totalCount
            }
        },
    )

    private val realItemsCount = MutableStateFlow(0)

    private var source = createSource()

    private val pageFlow = createPagingDataFlow(
        pageSize = pageSize,
        scope = scope,
        createSource = ::createSource,
        remoteItemsCount = realItemsCount,
        onSourceCreated = {
            source = it
            updateData()
        },
    )

    private fun updateData() {
        pagingData = pagingData.copy(
            source = source,
        )
    }

    private var pagingData = PagingData(
        pageFLow = pageFlow,
        source = source,
    )

    override fun getValue(thisRef: Any, property: KProperty<*>): PagingData<T> {
        return pagingData
    }
}

fun <T : Any, STATE : PagingState<T>> MviViewModel<STATE, *, *>.pagingDataHandler(
    pageSize: Int = 10,
    isRefreshFromFirstPage: () -> Boolean = { true },
    onUpdate: ((T) -> T)? = null,
    isSkipCancellationException: Boolean = false,
    pagedResponse: suspend (PagingDataHandlerState<STATE>) -> PagedResponse<T>,
    errorHandler: (suspend (PagingDataHandlerState<STATE>) -> Unit)? = null,
): ReadOnlyProperty<Any, PagingData<T>> {
    return PagingDelegate(
        scope = viewModelScope,
        pageSize = pageSize,
        isRefreshFromFirstPage = isRefreshFromFirstPage,
        onUpdate = onUpdate,
        pagedResponse = {
            val state = PagingDataHandlerState(
                page = it,
                state = container.stateFlow.value,
            )
            runCatching {
                pagedResponse(state)
            }.getOrElse { throwable ->
                if (isSkipCancellationException && throwable is CancellationException) {
                    throw throwable
                } else {
                    errorHandler?.invoke(state.copy(throwable = throwable))
                    throw throwable
                }
            }
        },
    )
}

interface PagingState<T : Any> {
    val pageFlow: PagingDataFlow<T>?
}

data class PagingDataHandlerState<STATE : Any>(
    val page: Int? = null,
    val state: STATE,
    val throwable: Throwable? = null,
)
