package cityinfo.io.core.uiKit.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cityinfo.io.core.uiKit.base.Colors

@Preview
@Composable
fun BaseLoadingUi(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        CircularProgressIndicator(
            color = Colors.BrandAccentColor,
            modifier = Modifier
                .align(alignment = Alignment.Center),
        )
    }
}
