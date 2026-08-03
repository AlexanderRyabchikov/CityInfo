@file:Suppress("unused", "UNUSED_PARAMETER")

package cityinfo.io.core.map.widgets.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.DpSize
import cityinfo.io.core.map.widgets.R
import com.yandex.runtime.image.ImageProvider
import androidx.core.graphics.createBitmap

@Composable
fun imageProvider(
    size: DpSize,
    content: @Composable () -> Unit,
): ImageProvider {
    val composeMapObjectRenderer = rememberComposeMapObjectRenderer()
    return remember(size, content) {
        composeMapObjectRenderer.toImageProvider(
            size = size,
            content = content,
        )
    }
}

@Composable
fun imageProvider(
    size: DpSize,
    key1: Any,
    content: @Composable () -> Unit,
): ImageProvider {
    val composeMapObjectRenderer = rememberComposeMapObjectRenderer()
    return remember(size, content, key1) {
        composeMapObjectRenderer.toImageProvider(
            size = size,
            content = content,
        )
    }
}

@Composable
fun imageProvider(
    size: DpSize,
    key1: Any,
    key2: Any,
    content: @Composable () -> Unit,
): ImageProvider {
    val composeMapObjectRenderer = rememberComposeMapObjectRenderer()
    return remember(size, content, key1, key2) {
        composeMapObjectRenderer.toImageProvider(
            size = size,
            content = content,
        )
    }
}

@Composable
fun imageProvider(
    size: DpSize,
    key1: Any,
    key2: Any,
    key3: Any,
    content: @Composable () -> Unit,
): ImageProvider {
    val composeMapObjectRenderer = rememberComposeMapObjectRenderer()
    return remember(size, content, key1, key2, key3) {
        composeMapObjectRenderer.toImageProvider(
            size = size,
            content = content,
        )
    }
}

@Composable
fun imageProvider(
    size: DpSize,
    vararg keys: Any,
    content: @Composable () -> Unit,
): ImageProvider {
    val composeMapObjectRenderer = rememberComposeMapObjectRenderer()
    return remember(size, content, keys) {
        composeMapObjectRenderer.toImageProvider(
            size = size,
            content = content,
        )
    }
}
@Composable
internal fun rememberComposeMapObjectRenderer(): ComposeMapObjectRenderer {
    val parentView = ensureContainerView()
    val parentCompositionContext = rememberCompositionContext()
    return remember {
        ComposeMapObjectRenderer(
            parentView = parentView,
            parentCompositionContext = parentCompositionContext,
        )
    }
}

@Composable
private fun ensureContainerView(): NoDrawContainerView {
    val view = LocalView.current as ViewGroup
    val context = LocalContext.current
    return view.findViewById(R.id.maps_compose_nodraw_container_view)
        ?: NoDrawContainerView(context)
            .apply { id = R.id.maps_compose_nodraw_container_view }
            .also(view::addView)
}

private class NoDrawContainerView(context: Context) : ViewGroup(context) {
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) = Unit
    override fun dispatchDraw(canvas: Canvas) = Unit
}

private val measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)

class ComposeMapObjectRenderer internal constructor(
    private val parentView: ViewGroup,
    private val parentCompositionContext: CompositionContext,
) {

    internal fun toImageProvider(
        size: DpSize,
        content: @Composable () -> Unit,
    ): ImageProvider {
        return renderComposableToImageProvider(
            parent = parentView,
            compositionContext = parentCompositionContext,
            content = content,
        )
    }
}

private fun renderComposableToImageProvider(
    parent: ViewGroup,
    compositionContext: CompositionContext,
    content: @Composable () -> Unit,
): ImageProvider {
    val fakeCanvas = Canvas()
    val composeView =
        ComposeView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )
            setParentCompositionContext(compositionContext)
            setContent(content)
        }.also(parent::addView)

    composeView.draw(fakeCanvas)
    composeView.measure(measureSpec, measureSpec)

    if (composeView.measuredWidth == 0 || composeView.measuredHeight == 0) {
        throw IllegalStateException(
            "The ComposeView was measured to have a width or height of " +
                "zero. Make sure that the content has a non-zero size.",
        )
    }

    composeView.layout(0, 0, composeView.measuredWidth, composeView.measuredHeight)

    val bitmap = createBitmap(composeView.measuredWidth, composeView.measuredHeight)

    Canvas(bitmap).apply {
        composeView.draw(this)
    }

    parent.removeView(composeView)

    return bitmap.toImageProvider()
}

fun Bitmap.toImageProvider(): ImageProvider {
    return ImageProvider.fromBitmap(this)
}
