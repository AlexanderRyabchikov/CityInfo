package cityinfo.io.core.base.mvi.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import cityinfo.io.core.base.mvi.stores.BaseAppStore
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.ParametersHolder
import org.koin.core.parameter.parametersOf

@Composable
inline fun <reified STORE : BaseAppStore<*, *, *>> rememberStore(
    parameters: ParametersHolder = parametersOf(),
    isCleanDisposeStore: Boolean = true,
    key: String? = null,
): STORE {
    val viewModel = koinViewModel<STORE>(
        key = key,
        parameters = { parameters },
    )
    DisposableEffect(Unit) {
        if (isCleanDisposeStore) {
            onDispose(viewModel::clearVM)
        } else {
            onDispose {}
        }
    }
    return viewModel
}
