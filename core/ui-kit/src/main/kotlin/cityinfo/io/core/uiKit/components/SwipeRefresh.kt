package cityinfo.io.core.uiKit.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeRefresh(
    isRefresh: Boolean,
    isRefreshingEnable: Boolean,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit = {},
    state: PullToRefreshState = rememberPullToRefreshState(),
    indicator: @Composable BoxScope.() -> Unit = {
        Indicator(
            modifier = Modifier.align(Alignment.TopCenter),
            isRefreshing = isRefresh,
            state = state,
        )
    },
    content: @Composable BoxScope.() -> Unit = {},
) {
    if (isRefreshingEnable) {
        PullToRefreshBox(
            modifier = modifier,
            isRefreshing = isRefresh,
            onRefresh = onRefresh,
            state = state,
            content = content,
            indicator = indicator,
        )
    } else {
        Box {
            content()
        }
    }
}
