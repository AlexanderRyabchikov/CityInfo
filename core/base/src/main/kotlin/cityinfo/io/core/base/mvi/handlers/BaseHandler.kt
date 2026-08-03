package cityinfo.io.core.base.mvi.handlers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel

interface BaseScreenHandler {

    fun onReload()

    fun onClickBack()

    fun onPullRefresh() {}
}

interface ScreenHandler<S : ViewModel> : BaseScreenHandler {
    val storeInstance: S?
    val store: S
        get() = requireNotNull(storeInstance) { "Nullable store может использоваться только в Preview" }
}

@Composable
inline fun <S : ViewModel, H : ScreenHandler<S>> rememberScreenHandler(
    key: S,
    crossinline calculation: @DisallowComposableCalls () -> H,
): H {
    return remember(key1 = key, calculation = calculation)
}
