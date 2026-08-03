package cityinfo.io.core.uiKit.errors

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cityinfo.io.core.network.ExceptionType
import cityinfo.io.core.uiKit.R
import cityinfo.io.core.uiKit.base.Colors
import cityinfo.io.core.uiKit.components.CityPrimaryButton
import cityinfo.io.core.uiKit.components.Text16
import cityinfo.io.core.uiKit.components.Text23Semibold

@Composable
fun ErrorContent(
    paddingValues: PaddingValues = PaddingValues(),
    error: ExceptionType? = null,
    onRetry: () -> Unit = {},
) {
    val (image, title, subtitle) = when (error) {
        is ExceptionType.UnknownHost -> Triple(
            first = R.drawable.ic_load_error,
            second = R.string.internet_connection_error_title,
            third = R.string.internet_connection_error_desc,
        )

        else -> Triple(
            first = R.drawable.ic_load_error,
            second = R.string.common_error_title,
            third = R.string.common_error_desc,
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Colors.White),
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
            )

            Text23Semibold(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(title),
                textAlign = TextAlign.Center,
                color = Colors.TextPrimary,
            )

            Text16(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(subtitle),
                color = Colors.TextSecondary,
                textAlign = TextAlign.Center,
            )
        }

        CityPrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            text = stringResource(R.string.error_button_reply_title),
            onClick = onRetry,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorContentNoInternetPreview() {
    ErrorContent(error = ExceptionType.UnknownHost)
}

@Preview(showBackground = true)
@Composable
private fun ErrorContentGeneralPreview() {
    ErrorContent(error = ExceptionType.General)
}