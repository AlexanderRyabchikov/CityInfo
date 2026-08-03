package cityinfo.io.core.uiKit.components

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import cityinfo.io.core.uiKit.base.Colors

private const val OFFSET_X = 100f
private const val OFFSET_Y = 100f

@Composable
fun LoadingSkeletonShimmer(
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
) {
    val gradient = listOf(
        Colors.Skeleton.copy(alpha = 0.08f),
        Colors.Skeleton.copy(alpha = 0.04f),
        Colors.Skeleton.copy(alpha = 0.08f),
    )
    LoadingSkeletonBase(
        modifier = modifier,
        shape = shape,
        gradient = gradient,
    )
}

@Composable
fun LoadingSkeletonBase(
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    gradient: List<Color>,
) {
    val transition = rememberInfiniteTransition(label = "LoadingSkeletonShimmer")

    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutLinearInEasing,
            ),
        ),
        label = "LoadingSkeletonShimmer",
    )
    val brush = linearGradient(
        colors = gradient,
        start = Offset(
            x = translateAnimation.value - OFFSET_X,
            y = translateAnimation.value - OFFSET_Y,
        ),
        end = Offset(
            x = translateAnimation.value,
            y = translateAnimation.value,
        ),
    )
    Spacer(
        modifier = Modifier
            .clip(shape)
            .background(brush)
            .then(modifier),
    )
}
