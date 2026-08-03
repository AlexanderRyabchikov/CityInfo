package cityinfo.io.feature.city.impl.screens.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cityinfo.io.core.base.managers.ScreenManager
import cityinfo.io.core.base.mvi.states.isAvailableContent
import cityinfo.io.core.base.screens.Screen
import cityinfo.io.core.base.screens.components.ScreenBottomBar
import cityinfo.io.core.base.screens.components.ScreenTopBar
import cityinfo.io.core.uiKit.components.CityPrimaryButton
import cityinfo.io.core.uiKit.content.DetailItemContent
import cityinfo.io.core.utils.toGroupedNumber
import cityinfo.io.feature.city.impl.R
import cityinfo.io.feature.city.impl.screens.detail.CityDetailHandler
import cityinfo.io.feature.city.impl.screens.detail.CityDetailState

@Composable
internal fun CityDetailContent(
    state: CityDetailState,
    handler: CityDetailHandler,
    screenManager: ScreenManager,
) {

    Screen(
        state = state,
        handler = handler,
        screenManager = screenManager,
        topBar = {
            ScreenTopBar(
                title = stringResource(R.string.city_detail_title),
                isAlignCenterMiddleContent = true,
                onClickBack = { handler.onClickBack() }
            )
        },
        bottomBar = {
            ScreenBottomBar(
                isShowShadow = false,
                isVisible = state.isAvailableContent
            ) {
                CityPrimaryButton(
                    text = stringResource(R.string.city_detail_search_info_button),
                    onClick = { handler.onSearchInBrowser() }
                )
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(vertical = 8.dp)
            ) {
                DetailItemContent(
                    title = stringResource(R.string.city_detail_title_city_name),
                    info = state.city.name
                )
                DetailItemContent(
                    title = stringResource(R.string.city_detail_title_country),
                    info = state.city.country
                )
                DetailItemContent(
                    title = stringResource(R.string.city_detail_title_population),
                    info = stringResource(
                        R.string.city_detail_population_value,
                        state.city.population.toGroupedNumber()
                    )
                )
            }
        }
    )
}


