package cityinfo.io.core.uiKit.search

import androidx.core.util.Consumer
import cityinfo.io.core.mvi.MviViewModel
import org.orbitmvi.orbit.blockingIntent

data class SearchStateInternal(
    val currentText: String = "",
    val debounce: Long = 1000L,
    val boundUpdateHints: Int = 3,
) {
    constructor(searchState: SearchState) : this (
        currentText = searchState.currentText,
        debounce = searchState.debounce,
        boundUpdateHints = searchState.boundUpdateHints,
    )
}

sealed interface SearchSideEffect {
    class Result(val result: SearchResult) : SearchSideEffect
    class ForceSetText(val text: String) : SearchSideEffect
    data object RequestFocus : SearchSideEffect
}

class SearchStore internal constructor(
    state: SearchStateInternal
) : MviViewModel<SearchStateInternal, SearchSideEffect, Nothing>(state) {

    override fun dispatch(action: Nothing) = Unit

    private var searchState: SearchState? = null

    fun onInit(searchState: SearchState) {
        this.searchState = searchState
        this.searchState?.outSideClickConsumer = Consumer { onClickOutside() }
        this.searchState?.requestFocusConsumer = Consumer { requestFocus() }
        this.searchState?.forceSetTextConsumer = Consumer(::forceSetText)
        this.searchState?.state = container.stateFlow
    }

    fun onDispose() {
        searchState = null
    }

    private fun requestFocus() {
        intent {
            postSideEffect(SearchSideEffect.RequestFocus)
        }
    }

    private fun forceSetText(text: String) {
        intent {
            reduce { state.copy(currentText = text) }
            postSideEffect(SearchSideEffect.ForceSetText(text))
        }
    }

    private fun onClickOutside() {
        intent {
            val result = SearchResult(type = SearchResultType.CANCEL, text = state.currentText)
            postSideEffect(SearchSideEffect.Result(result))
        }
    }

    fun onChangeText(text: String) {
        blockingIntent {
            val isCandidateTextEqualsCurrent = text == state.currentText

            reduce { state.copy(currentText = text) }

            if (text.isEmpty()) {
                val result = SearchResult(type = SearchResultType.CLEAR, text = text)
                postSideEffect(SearchSideEffect.Result(result))
                searchState?.onResult?.invoke(result)
            }

            if (!isCandidateTextEqualsCurrent && text.length >= state.boundUpdateHints) {
                val result = SearchResult(type = SearchResultType.CHANGE, text = text)
                postSideEffect(SearchSideEffect.Result(result))
                searchState?.onResult?.invoke(result)
            }
        }
    }

    fun onSearch(text: String) {
        intent {
            reduce { state.copy(currentText = text) }
            val result = SearchResult(type = SearchResultType.SEARCH, text = text)
            postSideEffect(SearchSideEffect.Result(result))
            searchState?.onResult?.invoke(result)
        }
    }
}