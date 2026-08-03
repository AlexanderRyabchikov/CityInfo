package cityinfo.io.core.uiKit.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import cityinfo.io.core.uiKit.base.Colors
import cityinfo.io.core.uiKit.base.R10Regular12
import cityinfo.io.core.uiKit.base.R12Semibold18
import cityinfo.io.core.uiKit.base.R16Medium22
import cityinfo.io.core.uiKit.base.R16Regular22
import cityinfo.io.core.uiKit.base.R18Semibold26
import cityinfo.io.core.uiKit.base.R19Semibold26
import cityinfo.io.core.uiKit.base.R23Semibold26

@Composable
fun Text10(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Colors.TextPrimary,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    Text(
        modifier = modifier,
        text = text,
        style = R10Regular12,
        textAlign = textAlign,
        color = color,
        maxLines = maxLines,
        overflow = overflow,
    )
}

@Composable
fun Text12Semibold(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Colors.TextPrimary,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    Text(
        modifier = modifier,
        text = text,
        style = R12Semibold18,
        textAlign = textAlign,
        color = color,
        maxLines = maxLines,
        overflow = overflow,
    )
}

@Composable
fun Text16(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Colors.TextPrimary,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    Text(
        modifier = modifier,
        text = text,
        style = R16Regular22,
        textAlign = textAlign,
        color = color,
        maxLines = maxLines,
        overflow = overflow,
    )
}

@Composable
fun Text16Medium(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Colors.TextPrimary,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    Text(
        modifier = modifier,
        text = text,
        style = R16Medium22,
        textAlign = textAlign,
        color = color,
        maxLines = maxLines,
        overflow = overflow,
    )
}

@Composable
fun Text18Semibold(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Colors.TextPrimary,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    Text(
        modifier = modifier,
        text = text,
        style = R18Semibold26,
        textAlign = textAlign,
        color = color,
        maxLines = maxLines,
        overflow = overflow,
    )
}

@Composable
fun Text19Semibold(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Colors.TextPrimary,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    Text(
        modifier = modifier,
        text = text,
        style = R19Semibold26,
        textAlign = textAlign,
        color = color,
        maxLines = maxLines,
        overflow = overflow,
    )
}

@Composable
fun Text23Semibold(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Colors.TextPrimary,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    Text(
        modifier = modifier,
        text = text,
        style = R23Semibold26,
        textAlign = textAlign,
        color = color,
        maxLines = maxLines,
        overflow = overflow,
    )
}