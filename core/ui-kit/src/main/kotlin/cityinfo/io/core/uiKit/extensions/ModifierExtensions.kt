package cityinfo.io.core.uiKit.extensions

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cityinfo.io.core.uiKit.base.Colors

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.required(condition: Boolean, modifier: @Composable Modifier.() -> Modifier): Modifier =
    composed {
        if (condition) this.then(modifier()) else this
    }

fun Modifier.rippleClickable(
    enabled: Boolean = true,
    radius: Dp = Dp.Unspecified,
    isBounded: Boolean = false,
    onClick: () -> Unit,
): Modifier = composed {
    clickable(
        interactionSource = remember { MutableInteractionSource() },
        enabled = enabled,
        indication = if (enabled) ripple(radius = radius, bounded = isBounded) else null,
        role = Role.Button,
        onClick = onClick,
    )
}

fun Modifier.bottomShadow() =
    advancedShadow(
        offsetY = 10.dp,
        shadowBlurRadius = 2.dp,
        color = Colors.ShadowPrimary,
        alpha = 0.12f,
        cornersRadius = 0.dp,
    ).advancedShadow(
        offsetY = (-1).dp,
        shadowBlurRadius = 2.dp,
        color = Colors.ShadowPrimary,
        alpha = 0.12f,
        cornersRadius = 0.dp,
    )

@Suppress("LongParameterList")
fun Modifier.advancedShadow(
    color: Color = Color.Black,
    alpha: Float = 1f,
    cornersRadius: Dp = 0.dp,
    shadowBlurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    bottomInset: Dp = 0.dp,
) = drawBehind {
    val shadowColor = color.copy(alpha = alpha).toArgb()
    val transparentColor = color.copy(alpha = 0f).toArgb()
    drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowBlurRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor,
        )
        it.drawRoundRect(
            left = 0f,
            top = 0f,
            right = this.size.width,
            bottom = this.size.height - bottomInset.toPx(),
            radiusX = cornersRadius.toPx(),
            radiusY = cornersRadius.toPx(),
            paint = paint,
        )
    }
}