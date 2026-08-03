package cityinfo.io.feature.city.impl.screens.cities.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cityinfo.io.core.base.managers.ScreenManager
import cityinfo.io.core.base.screens.Screen
import cityinfo.io.core.base.screens.components.ScreenTopBar
import cityinfo.io.core.paging.compose.PagingLazyList
import cityinfo.io.core.paging.compose.collectAsLazyPagingItemsOrEmpty
import cityinfo.io.core.paging.compose.pagingItemsIndexed
import cityinfo.io.core.uiKit.base.Colors
import cityinfo.io.core.uiKit.search.SearchField
import cityinfo.io.core.uiKit.search.rememberSearchState
import cityinfo.io.feature.city.impl.R
import cityinfo.io.feature.city.impl.screens.cities.CitiesHandler
import cityinfo.io.feature.city.impl.screens.cities.CitiesState

@Composable
internal fun CitiesContent(
    state: CitiesState,
    handler: CitiesHandler,
    screenManager: ScreenManager
) {
    val data = state.pageFlow.collectAsLazyPagingItemsOrEmpty()

    val searchState = rememberSearchState(
        debounce = 1000L,
        boundUpdateHints = 3,
        currentText = state.query,
        onResult = {
            if (it.isChanged) {
                handler.onSearch(it.text)
            }
        },
    )

    Screen(
        state = state,
        handler = handler,
        screenManager = screenManager,
        topBar = {
            ScreenTopBar(
                titleColor = Colors.TextPrimary,
                isAlignCenterMiddleContent = true,
                title = stringResource(R.string.city_list_title)
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                SearchField(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    searchState = searchState,
                    placeholder = stringResource(R.string.city_list_search_hint),
                )

                PagingLazyList(
                    modifier = Modifier
                        .fillMaxWidth(),
                    data = data,
                    emptyContent = { EmptyState() },
                    loadingContent = { LoadingState() },
                ) {
                    pagingItemsIndexed(data) { index, item ->

                        CityItemContent(
                            city = item,
                            onClick = { handler.onCityClick(item) }
                        )

                        if (index < data.itemCount - 1) {
                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                color = Colors.DividerPrimary,
                            )
                        }
                    }
                }
            }
        }
    )
}