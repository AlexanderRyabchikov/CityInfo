package cityinfo.io.core.uiKit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cityinfo.io.core.uiKit.base.Colors
import cityinfo.io.core.uiKit.base.R18Semibold26
import cityinfo.io.core.uiKit.base.Shapes
import cityinfo.io.core.uiKit.extensions.rippleClickable

@Composable
fun CityPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    shape: Shape = Shapes.Shape16,
    textStyle: TextStyle = R18Semibold26,
    textColor: Color = Colors.ButtonPrimaryForeground,
    backgroundColor: Color = Colors.ButtonPrimaryBackground,
) {
    Row(
        modifier = modifier
            .clip(shape)
            .background(backgroundColor)
            .rippleClickable(
                onClick = onClick,
                isBounded = true,
                enabled = !isLoading,
            )
            .padding(horizontal = 42.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(18.dp)
                        .align(Alignment.Center),
                    color = backgroundColor,
                    trackColor = Colors.White,
                    strokeWidth = 2.dp,
                )
            }
        } else {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = text,
                style = textStyle,
                color = textColor,
                maxLines = 1,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun CityPrimaryButtonPreview() {
    CityPrimaryButton(
        text = "Button",
        onClick = {}
    )
}