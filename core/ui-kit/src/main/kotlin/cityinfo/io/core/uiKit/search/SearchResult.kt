package cityinfo.io.core.uiKit.search

data class SearchResult(
    val type: SearchResultType,
    val text: String,
) {
    val isChanged: Boolean
        get() = type != SearchResultType.CANCEL
}

enum class SearchResultType {
    CHANGE, SEARCH, CLEAR, CANCEL
}