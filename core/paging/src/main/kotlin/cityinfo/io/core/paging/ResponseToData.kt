package cityinfo.io.core.paging

import cityinfo.io.core.paging.core.PagedResponse

val <T> PagedResponse<T>.data
    get() = PagedData(
        items = items,
        page = page,
        limit = limit,
        totalCount = totalCount,
    )
