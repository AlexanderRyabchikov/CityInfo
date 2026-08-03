package cityinfo.io.core.uiKit.search

class SearchHandler(
    private val store: SearchStore,
) {

    fun onChangeText(text: String) = store.onChangeText(text = text)

    fun onSearch(text: String) = store.onSearch(text = text)
}