package cityinfo.io.core.base.mvi.stores

import cityinfo.io.core.base.mvi.states.BaseState
import cityinfo.io.core.network.ExceptionType
import cityinfo.io.core.network.toExceptionType
import cityinfo.io.core.paging.PagedData
import cityinfo.io.core.paging.core.PagingDataFlow
import cityinfo.io.core.paging.core.PagingItem
import cityinfo.io.core.paging.core.PagingState
import cityinfo.io.core.paging.core.pagingDataHandler


abstract class BasePagingStore<STATE, SIDE_EFFECT : Any, EVENT : Any, PAGE_ITEM>(
    initialState: STATE,
    private val isSkipCancellationException: Boolean = false,
) : BaseAppStore<STATE, SIDE_EFFECT, EVENT>(
    initialState = initialState,
) where STATE : BaseState,
        STATE : PagingState<PAGE_ITEM>,
        PAGE_ITEM : PagingItem {

    protected val pagingData by pagingDataHandler(
        isSkipCancellationException = isSkipCancellationException,
        errorHandler = {
            if (it.page == 0) {
                onPagingError(it.throwable?.toExceptionType())
            }
        },
        pagedResponse = { data ->
            onLoadPage(
                page = data.page,
                state = data.state,
            ).also {
                if (data.page == 0) {
                    onLoadFirstPage()
                }
            }
        },
    )

    init {
        intent {
            onInit(pagingData.pageFLow)
        }
    }

    abstract fun onInit(flow: PagingDataFlow<PAGE_ITEM>)

    abstract fun onPagingError(error: ExceptionType?)

    abstract fun onLoadFirstPage()

    abstract suspend fun onLoadPage(page: Int?, state: STATE): PagedData<PAGE_ITEM>
}
