package cityinfo.io.core.paging.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import cityinfo.io.core.network.toExceptionType
import cityinfo.io.core.paging.core.PagingItem
import cityinfo.io.core.uiKit.errors.ErrorContent
import cityinfo.io.core.uiKit.pagination.PaginationError
import cityinfo.io.core.uiKit.pagination.PagingLoadPage

@Composable
fun <T : Any> PagingLazyList(
    modifier: Modifier = Modifier,
    data: LazyPagingItems<T>,
    lazyListState: LazyListState = rememberLazyListState(),
    isEmptyOptionally: Boolean = true,
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    emptyContent: @Composable () -> Unit,
    appendLoadingContent: @Composable () -> Unit = { PagingLoadPage() },
    appendErrorContent: @Composable () -> Unit = { PaginationError(onClickRefresh = data::retry) },
    loadingContent: @Composable () -> Unit,
    errorContent: @Composable (Throwable?) -> Unit = {
        ErrorContent(
            paddingValues = PaddingValues(16.dp),
            error = it?.toExceptionType(),
            onRetry = data::retry
        )
    },
    requiredContent: @Composable (PagingListState) -> Unit = {},
    content: LazyListScope.() -> Unit,
) {
    val loadState = data.loadState

    val isEmpty by remember(data) {
        derivedStateOf {
            val countRemoved = data.totalItemCount
            data.itemSnapshotList.isEmpty() || countRemoved == 0
        }
    }

    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        reverseLayout = reverseLayout,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        contentPadding = contentPadding,
    ) {
        when {
            loadState.refresh is LoadState.Error -> {
                item {
                    FillMaxSizeColumn(
                        content = { requiredContent(PagingListState.ERROR) },
                        fillMaxContent = { errorContent((loadState.refresh as? LoadState.Error)?.error) },
                    )
                }
            }

            loadState.refresh is LoadState.Loading -> {
                item {
                    FillMaxSizeColumn(
                        content = { requiredContent(PagingListState.LOADING) },
                        fillMaxContent = { loadingContent() },
                    )
                }
            }

            isEmpty && isEmptyOptionally -> {
                item {
                    FillMaxSizeColumn(
                        content = { requiredContent(PagingListState.EMPTY) },
                        fillMaxContent = { emptyContent() },
                    )
                }
            }

            else -> {
                item { requiredContent(PagingListState.DEFAULT) }
                content()
                loadState.apply {
                    when (append) {
                        is LoadState.Loading -> {
                            item { appendLoadingContent() }
                        }

                        is LoadState.Error -> {
                            item { appendErrorContent() }
                        }

                        is LoadState.NotLoading -> {}
                    }
                }
            }
        }
    }
}

enum class PagingListState {
    DEFAULT,
    LOADING,
    ERROR,
    EMPTY,
}

fun <T : PagingItem> LazyListScope.pagingItemsIndexed(
    data: LazyPagingItems<T>,
    content: @Composable (Int, T) -> Unit,
) {
    items(
        count = data.itemCount,
        key = data.itemKey { it.pagingId },
    ) { index ->
        data[index]?.also { item ->
            content(index, item)
        }
    }
}

@Composable
private fun LazyItemScope.FillMaxSizeColumn(
    content: @Composable () -> Unit,
    fillMaxContent: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillParentMaxHeight(),
        content = {
            content()
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                fillMaxContent()
            }
        },
    )
}
