package cityinfo.io.core.paging.core

interface PagedResponse<T> {
    val items: List<T>?
    val page: Int?
    val totalCount: Int
    val limit: Int
}
