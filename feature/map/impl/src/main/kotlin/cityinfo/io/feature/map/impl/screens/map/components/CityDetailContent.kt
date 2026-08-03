package cityinfo.io.feature.map.impl.screens.map.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cityinfo.io.core.uiKit.components.CityPrimaryButton
import cityinfo.io.core.uiKit.content.DetailItemContent
import cityinfo.io.core.utils.toGroupedNumber
import cityinfo.io.feature.map.api.models.CityMapMarkerData
import cityinfo.io.feature.map.impl.R
import cityinfo.io.feature.map.impl.screens.map.CityMapHandler

@Composable
internal fun CityDetailContent(
    cityMap: CityMapMarkerData,
    handler: CityMapHandler,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        DetailItemContent(
            title = stringResource(R.string.city_map_title_city_name),
            info = cityMap.name
        )
        DetailItemContent(
            title = stringResource(R.string.city_map_title_country),
            info = cityMap.country
        )
        DetailItemContent(
            title = stringResource(R.string.city_map_title_population),
            info = stringResource(
                R.string.city_map_population_value,
                cityMap.population.toGroupedNumber()
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        CityPrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = stringResource(R.string.city_map_search_info_button),
            onClick = { handler.onSearchInBrowser() }
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}