package cityinfo.io.core.mvi

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.annotation.OrbitDsl
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.cancellation.CancellationException

@OrbitDsl
suspend fun launch(
    ceh: CoroutineExceptionHandler? = null,
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend () -> Unit,
) {
    coroutineScope {
        launch(context) {
            runCatching {
                block.invoke()
            }.onFailure { e ->
                if (e is CancellationException) throw e
                ceh?.handleException(coroutineContext, e)
            }
        }
    }
}