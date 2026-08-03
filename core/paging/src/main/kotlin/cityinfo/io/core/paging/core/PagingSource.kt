package cityinfo.io.core.paging.core

import androidx.paging.PagingSource
import androidx.paging.PagingState

class PagingSource<T : Any>(
    private val pagedResponse: suspend (Int) -> PagedResponse<T>,
    private val isRefreshFromFirstPage: () -> Boolean = { false },
) : PagingSource<Int, T>() {

    override suspend fun load(
        params: LoadParams<Int>,
    ): LoadResult<Int, T> = runCatching {
        val nextPageNumber = params.key ?: 1
        val response = pagedResponse(nextPageNumber)
        val prevKey = nextPageNumber.takeIf { it > 1 }?.dec()
        val limit = response.limit
        val nextKey = nextPageNumber.takeIf {
            limit <= (response.items?.size ?: 0)
        }?.inc()
        LoadResult.Page(
            data = response.items ?: emptyList(),
            prevKey = prevKey,
            nextKey = nextKey,
        )
    }.getOrElse { LoadResult.Error(it) }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        if (isRefreshFromFirstPage()) return 0
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(10) ?: anchorPage?.nextKey?.minus(10)
        }
    }
}
