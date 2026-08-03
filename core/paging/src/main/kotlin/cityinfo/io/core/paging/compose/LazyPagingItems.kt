package cityinfo.io.core.paging.compose

import androidx.compose.runtime.Composable
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import cityinfo.io.core.paging.core.PagingDataFlow

class LazyPagingItems<T : Any>(
    private val items: LazyPagingItems<T>,
    private val realItemsCount: StateFlow<Int>,
) {

    val totalItemCount: Int get() = realItemsCount.value

    val itemSnapshotList = items.itemSnapshotList

    val itemCount: Int = items.itemCount

    val loadState: CombinedLoadStates = items.loadState

    val isLoading: Boolean = items.loadState.refresh is LoadState.Loading

    operator fun get(index: Int): T? = items[index]

    fun peek(index: Int): T? = items.peek(index)

    fun retry() = items.retry()

    fun refresh() = items.refresh()

    fun itemKey(key: ((item: T) -> Any)? = null) = items.itemKey(key)

    fun isEmpty() = itemCount == 0

    fun isNotEmpty() = !isEmpty()
}

@Composable
fun <T : Any> PagingDataFlow<T>.collectAsLazyPagingItems(): cityinfo.io.core.paging.compose.LazyPagingItems<T> {
    return LazyPagingItems(items = flow.collectAsLazyPagingItems(), realItemsCount = remoteItemsCount)
}

@Composable
fun <T : Any> PagingDataFlow<T>?.collectAsLazyPagingItemsOrEmpty(): cityinfo.io.core.paging.compose.LazyPagingItems<T> {
    val flow = (this?.flow ?: emptyFlow()).collectAsLazyPagingItems()
    val realItemsCount = (this?.remoteItemsCount ?: MutableStateFlow(0))
    return LazyPagingItems(items = flow, realItemsCount = realItemsCount)
}
