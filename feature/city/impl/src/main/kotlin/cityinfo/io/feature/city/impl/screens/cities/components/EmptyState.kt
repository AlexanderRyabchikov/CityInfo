package cityinfo.io.feature.city.impl.screens.cities.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cityinfo.io.core.uiKit.base.Colors
import cityinfo.io.core.uiKit.components.Text12Semibold
import cityinfo.io.core.uiKit.components.Text16Medium
import cityinfo.io.feature.city.impl.R

@Preview
@Composable
internal fun EmptyState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.White)
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text16Medium(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.city_list_empty_title),
                color = Colors.TextPrimary,
                textAlign = TextAlign.Center
            )

            Text12Semibold(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.city_list_empty_desc),
                color = Colors.TextSecondary,
                textAlign = TextAlign.Center
            )
        }
    }
}