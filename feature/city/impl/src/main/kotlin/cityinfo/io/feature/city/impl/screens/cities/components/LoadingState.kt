package cityinfo.io.feature.city.impl.screens.cities.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cityinfo.io.core.uiKit.base.Colors
import cityinfo.io.core.uiKit.base.Shapes
import cityinfo.io.core.uiKit.components.LoadingSkeletonShimmer
import cityinfo.io.feature.city.impl.R

@Preview
@Composable
internal fun LoadingState() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Colors.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        repeat(5) { index ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_location),
                    contentDescription = null,
                    tint = Colors.IconSecondary,
                )

                LoadingSkeletonShimmer(
                    modifier = Modifier
                        .width(
                            if (index % 2 == 0) 144.dp else 180.dp
                        )
                        .height(22.dp),
                    shape = Shapes.Shape12
                )
            }


            if (index < 4) {
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