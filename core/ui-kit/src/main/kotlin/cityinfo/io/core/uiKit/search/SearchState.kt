package cityinfo.io.core.uiKit.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.util.Consumer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun rememberSearchState(
    debounce: Long = 1000L,
    currentText: String = "",
    boundUpdateHints: Int = 4,
    onResult: (SearchResult) -> Unit,
): SearchState {
    return remember {
        SearchState(
            debounce = debounce,
            currentText = currentText,
            boundUpdateHints = boundUpdateHints,
            onResult = onResult,
        )
    }
}

class SearchState(
    internal val debounce: Long = 1000L,
    internal val currentText: String = "",
    internal val boundUpdateHints: Int = 3,
    internal val onResult: (SearchResult) -> Unit,
) {
    internal var outSideClickConsumer: Consumer<Unit>? = null
    internal var requestFocusConsumer: Consumer<Unit>? = null
    internal var forceSetTextConsumer: Consumer<String>? = null
    internal var state: StateFlow<SearchStateInternal> = MutableStateFlow(SearchStateInternal())

    fun requestFocus() {
        requestFocusConsumer?.accept(Unit)
    }

    fun setText(text: String) {
        forceSetTextConsumer?.accept(text)
    }

    internal fun outsideClick() {
        outSideClickConsumer?.accept(Unit)
    }
}