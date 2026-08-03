package cityinfo.io.core.uiKit.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cityinfo.io.core.uiKit.base.Colors
import cityinfo.io.core.uiKit.components.Text16
import cityinfo.io.core.uiKit.components.Text16Medium

@Composable
fun DetailItemContent(
    title: String,
    info: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text16Medium(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            color = Colors.TextPrimary,
            textAlign = TextAlign.Start
        )
        Text16(
            modifier = Modifier.fillMaxWidth(),
            text = info,
            color = Colors.TextPrimary,
            textAlign = TextAlign.Start
        )
    }
}