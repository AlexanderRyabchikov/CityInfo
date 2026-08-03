package cityinfo.io.core.uiKit.pagination

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cityinfo.io.core.uiKit.base.Colors

@Preview
@Composable
fun PagingLoadPage() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, bottom = 40.dp),
    ) {
        CircularProgressIndicator(
            color = Colors.BrandAccentColor,
            strokeWidth = 2.dp,
            modifier = Modifier
                .size(16.dp)
                .align(alignment = Alignment.Center),
        )
    }
}