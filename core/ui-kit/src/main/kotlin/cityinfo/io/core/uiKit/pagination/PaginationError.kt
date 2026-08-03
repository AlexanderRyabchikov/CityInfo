package cityinfo.io.core.uiKit.pagination

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cityinfo.io.core.uiKit.R
import cityinfo.io.core.uiKit.components.Text12Semibold
import cityinfo.io.core.uiKit.components.Text16Medium

@Composable
fun PaginationError(
    onClickRefresh: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier.size(48.dp),
            painter = painterResource(id = R.drawable.ic_load_error),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text16Medium(
            text = stringResource(R.string.common_error_title),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text12Semibold(
            text = stringResource(R.string.common_error_desc),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(24.dp))

        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onClick = onClickRefresh
        ) {
            Text16Medium(
                text = stringResource(R.string.error_button_reply_title),
            )
        }
    }
}

@Preview
@Composable
private fun PaginationErrorPreview() {
    PaginationError { }
}