package cityinfo.io.core.uiKit.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.AppBarDefaults
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cityinfo.io.core.uiKit.R
import cityinfo.io.core.uiKit.base.Colors

@Composable
fun ScreenTopBarBack(
    modifier: Modifier = Modifier,
    title: String? = null,
    subtitle: String? = null,
    isLoading: Boolean = false,
    isAlignCenterMiddleContent: Boolean = false,
    backgroundColor: Color = Colors.White,
    colorBackArrow: Color = Colors.IconPrimary,
    colorTitle: Color = Colors.TextPrimary,
    onClickBack: (() -> Unit)?,
    leftIconRes: Int = R.drawable.ic_arrow_back,
    leftContent: (@Composable () -> Unit)? = {
        AnimatedVisibility(
            visible = onClickBack != null,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            IconButton(onClick = { onClickBack?.invoke() }) {
                Icon(
                    painter = painterResource(leftIconRes),
                    contentDescription = null,
                    tint = colorBackArrow,
                )
            }
        }
    },
    middleContent: (@Composable () -> Unit)? = {
        AnimatedVisibility(
            visible = title != null,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Column {
                if (!isLoading) {
                    Text16Medium(text = title.orEmpty(), color = colorTitle, maxLines = 1)
                    subtitle?.also {
                        Text10(
                            text = subtitle,
                            color = Colors.TextTertiary,
                            maxLines = 1,
                        )
                    }
                } else {
                    LoadingSkeletonShimmer(
                        modifier = Modifier
                            .fillMaxWidth(.4f)
                            .height(16.dp),
                    )
                }
            }
        }
    },
    rightContent: (@Composable RowScope.() -> Unit)? = null,
) {
    ScreenTopBar(
        modifier = Modifier
            .background(backgroundColor)
            .then(modifier),
        backgroundColor = backgroundColor,
        isAlignCenterMiddleContent = isAlignCenterMiddleContent,
        leftContent = { leftContent?.invoke() },
        middleContent = { middleContent?.invoke() },
        rightContent = { rightContent?.invoke(this) },
    )
}

@Composable
private fun ScreenTopBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Colors.White,
    contentColor: Color = Colors.TextPrimary,
    isAlignCenterMiddleContent: Boolean = false,
    elevation: Dp = 0.dp,
    contentPadding: PaddingValues = AppBarDefaults.ContentPadding,
    leftContent: (@Composable () -> Unit)? = null,
    middleContent: (@Composable () -> Unit)? = null,
    rightContent: (@Composable RowScope.() -> Unit)? = null,
) {
    TopAppBar(
        modifier = modifier,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = elevation,
        contentPadding = contentPadding,
        content = {
            if (isAlignCenterMiddleContent) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterStart),
                    ) {
                        leftContent?.invoke()
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center),
                    ) {
                        middleContent?.invoke()
                    }
                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterEnd),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        rightContent?.invoke(this)
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        leftContent?.invoke()
                    }
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        middleContent?.invoke()
                    }
                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        rightContent?.invoke(this)
                    }
                }
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun ScreenTopBarBackPreview() {
    ScreenTopBarBack(
        title = "Москва",
        subtitle = "Россия",
        onClickBack = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun ScreenTopBarBackWithoutBackPreview() {
    ScreenTopBarBack(
        title = "Москва",
        subtitle = "Россия",
        onClickBack = null,
    )
}

@Preview(showBackground = true)
@Composable
private fun ScreenTopBarBackWithoutBackCenterAlignPreview() {
    ScreenTopBarBack(
        title = "Москва",
        subtitle = "Россия",
        onClickBack = null,
        isAlignCenterMiddleContent = true,
    )
}

@Preview(showBackground = true)
@Composable
private fun ScreenTopBarBackLoadingPreview() {
    ScreenTopBarBack(
        title = "Москва",
        isLoading = true,
        onClickBack = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun ScreenTopBarBackCenteredPreview() {
    ScreenTopBarBack(
        title = "Москва",
        isAlignCenterMiddleContent = true,
        onClickBack = {},
        rightContent = {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.ic_map),
                    contentDescription = null,
                    tint = Colors.IconPrimary,
                )
            }
        },
    )
}