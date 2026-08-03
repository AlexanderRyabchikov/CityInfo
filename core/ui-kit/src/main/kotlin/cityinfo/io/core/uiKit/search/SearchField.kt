package cityinfo.io.core.uiKit.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import cityinfo.io.core.uiKit.base.Colors
import cityinfo.io.core.uiKit.base.R16Medium22
import cityinfo.io.core.uiKit.base.Shapes
import cityinfo.io.core.uiKit.search.components.SearchComponent
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    searchState: SearchState,
    placeholder: String,
    backgroundColor: Color = Colors.BackgroundTertiaryColor,
    focusedBackgroundColor: Color = Colors.White,
    focusedBorderColor: Color = Colors.BrandAccentColor,
    shape: Shape = Shapes.Shape16,
    hintTextColor: Color = Colors.TextTertiary,
    searchTextColor: Color = Colors.TextPrimary,
    textStyle: TextStyle = R16Medium22,
) {

    val store = viewModel { SearchStore(SearchStateInternal(searchState)) }
    val handler = remember(key1 = store) { SearchHandler(store = store) }
    val state by store.collectAsState()

    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    DisposableEffect(searchState) {
        store.onInit(searchState)
        onDispose(store::onDispose)
    }

    val search = remember { mutableStateOf(state.currentText) }

    store.collectSideEffect { effect ->
        when (effect) {
            is SearchSideEffect.Result -> {
                when (effect.result.type) {
                    SearchResultType.SEARCH, SearchResultType.CANCEL -> {
                        keyboard?.hide()
                        focusManager.clearFocus(true)
                    }

                    else -> Unit
                }
            }

            is SearchSideEffect.RequestFocus -> {
                runCatching {
                    focusRequester.requestFocus()
                    keyboard?.show()
                }
            }

            is SearchSideEffect.ForceSetText -> {
                search.value = ""
                delay(100.milliseconds) // Для анимации изменения текста
                search.value = effect.text
                keyboard?.hide()
                focusManager.clearFocus(true)
            }
        }
    }

    SearchComponent(
        modifier = modifier,
        state = state,
        handler = handler,
        placeholder = placeholder,
        focusRequester = focusRequester,
        search = search,
        backgroundColor = backgroundColor,
        focusedBackgroundColor = focusedBackgroundColor,
        focusedBorderColor = focusedBorderColor,
        shape = shape,
        hintTextColor = hintTextColor,
        searchTextColor = searchTextColor,
        textStyle = textStyle,
    )
}