package cityinfo.io.feature.city.impl.screens.cities.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cityinfo.io.core.uiKit.base.Colors
import cityinfo.io.core.uiKit.components.Text16Medium
import cityinfo.io.core.uiKit.extensions.rippleClickable
import cityinfo.io.feature.city.api.models.City
import cityinfo.io.feature.city.impl.R

@Composable
internal fun CityItemContent(
    city: City,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .rippleClickable(
                onClick = onClick,
                isBounded = true,
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_location),
            contentDescription = null,
            tint = Colors.IconSecondary,
        )

        Text16Medium(
            text = "${city.name}, ${city.country}",
            color = Colors.TextPrimary,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CityItemContentPreview() {
    CityItemContent(
        city = City(
            id = 1,
            name = "Москва",
            country = "Россия",
            longitude = 37.6173,
            latitude = 55.7558,
            population = 12_655_050,
        ),
        onClick = {},
    )
}