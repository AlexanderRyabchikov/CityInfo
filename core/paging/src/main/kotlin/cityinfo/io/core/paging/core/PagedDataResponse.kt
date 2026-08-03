package cityinfo.io.core.paging.core

data class PagedDataResponse<T>(
    val items: List<T>? = null,
    val totalCount: Int? = null,
    val page: Int? = null,
    val limit: Int? = null,
)
