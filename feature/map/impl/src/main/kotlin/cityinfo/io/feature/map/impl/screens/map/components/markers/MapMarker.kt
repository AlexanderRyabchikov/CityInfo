package cityinfo.io.feature.map.impl.screens.map.components.markers

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Typeface
import android.util.TypedValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import cityinfo.io.core.uiKit.base.Colors
import cityinfo.io.core.map.widgets.components.MapMarkerImageProvider
import cityinfo.io.core.map.widgets.components.MapMarkerImageProviderData
import com.yandex.runtime.image.ImageProvider
import androidx.core.graphics.createBitmap

internal class MapMarker(private val ctx: Context) : MapMarkerImageProvider {

    companion object {
        private const val PIN_HEIGHT = 32f
        private const val PIN_RADIUS = 8f
        private const val PIN_MIN_WIDTH = 32f
        private const val PIN_HORIZONTAL_PADDING = 12f
        private const val ARROW_WIDTH = 6f
        private const val ARROW_HEIGHT = 4f
        private const val TEXT_SIZE = 12f
        private const val TEXT_WEIGHT = 500
    }

    private val pinHeight = PIN_HEIGHT.toPx()
    private val pinRadius = PIN_RADIUS.toPx()
    private val pinMinWidth = PIN_MIN_WIDTH.toPx()
    private val pinHorizontalPadding = PIN_HORIZONTAL_PADDING.toPx()
    private val arrowWidth = ARROW_WIDTH.toPx()
    private val arrowHeight = ARROW_HEIGHT.toPx()

    private val textPaint = Paint().apply {
        color = Colors.White.toArgb()
        textSize = TEXT_SIZE.toPx()
        textAlign = Paint.Align.CENTER
        style = Paint.Style.FILL
        typeface = Typeface.create(Typeface.SANS_SERIF, TEXT_WEIGHT, false)
        isAntiAlias = true
    }

    private val backgroundPaint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    override fun imageProvider(data: MapMarkerImageProviderData): ImageProvider {
        val text = data.text
        val width = maxOf(pinMinWidth, textPaint.measureText(text) + pinHorizontalPadding * 2f)

        backgroundPaint.color = data.backgroundColor.toArgb()

        val bitmap = createBitmap(width.toInt(), (pinHeight + arrowHeight).toInt())

        Canvas(bitmap).apply {
            drawBackground(width)
            drawArrow(width)
            drawTitle(text, width)
        }

        return ImageProvider.fromBitmap(bitmap)
    }

    private val MapMarkerImageProviderData.text: String
        get() = when (this) {
            is CityMapMarkerImage.Pin -> name
            is CityMapMarkerImage.Cluster -> count.toString()
            else -> ""
        }

    private val MapMarkerImageProviderData.backgroundColor: Color
        get() = if (this is CityMapMarkerImage.Pin && isSelected) {
            Colors.BrandColor
        } else {
            Colors.PinPrimaryBackground
        }

    private fun Canvas.drawBackground(width: Float) {
        drawRoundRect(0f, 0f, width, pinHeight, pinRadius, pinRadius, backgroundPaint)
    }

    private fun Canvas.drawArrow(width: Float) {
        val centerX = width / 2f

        val path = Path().apply {
            fillType = Path.FillType.EVEN_ODD
            moveTo(centerX - arrowWidth / 2f, pinHeight)
            lineTo(centerX, pinHeight + arrowHeight)
            lineTo(centerX + arrowWidth / 2f, pinHeight)
            close()
        }

        drawPath(path, backgroundPaint)
    }

    private fun Canvas.drawTitle(text: String, width: Float) {
        val fontMetrics = textPaint.fontMetrics
        val baseline = pinHeight / 2f - (fontMetrics.ascent + fontMetrics.descent) / 2f

        drawText(text, width / 2f, baseline, textPaint)
    }

    private fun Float.toPx(): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        ctx.resources.displayMetrics,
    )
}