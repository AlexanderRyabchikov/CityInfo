package cityinfo.io.core.paging

import cityinfo.io.core.paging.core.PagedResponse

data class PagedData<T>(
    override val items: List<T>?,
    override val limit: Int,
    override val page: Int?,
    override val totalCount: Int,
) : PagedResponse<T> {

    companion object {
        fun <T> empty(): PagedData<T> {
            return PagedData(
                items = emptyList(),
                page = 1,
                limit = 1,
                totalCount = 0,
            )
        }
    }
}
