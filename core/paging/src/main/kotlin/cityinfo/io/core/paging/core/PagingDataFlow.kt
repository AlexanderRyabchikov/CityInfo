package cityinfo.io.core.paging.core

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class PagingDataFlow<T : Any>(
    val flow: Flow<PagingData<T>>,
    val remoteItemsCount: StateFlow<Int>,
)

fun <T : Any> createPagingDataFlow(
    pageSize: Int,
    scope: CoroutineScope,
    createSource: () -> PagingSource<T>,
    remoteItemsCount: StateFlow<Int> = MutableStateFlow(0),
    onSourceCreated: (PagingSource<T>) -> Unit,
): PagingDataFlow<T> {
    return Pager(
        config = PagingConfig(
            pageSize = pageSize,
            initialLoadSize = pageSize,
        ),
        pagingSourceFactory = {
            createSource().also { onSourceCreated(it) }
        },
    ).let {
        PagingDataFlow(
            flow = it.flow.cachedIn(scope),
            remoteItemsCount = remoteItemsCount,
        )
    }
}
