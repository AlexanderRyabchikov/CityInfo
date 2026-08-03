package cityinfo.io.init

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ModalBottomSheetState
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberModalBottomSheetState
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.navigation.BottomSheetNavigator
import androidx.compose.material.navigation.ModalBottomSheetLayout
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme as MaterialTheme3
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import cityinfo.io.core.navigation.LocalNavController
import cityinfo.io.core.uiKit.base.Colors
import cityinfo.io.core.uiKit.base.Shapes
import cityinfo.io.navigation.BottomNavigationBar

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun AppContainer(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        shapes = MaterialTheme.shapes.copy(
            medium = Shapes.Shape12,
        ),
        colors = MaterialTheme.colors.copy(
            background = Colors.White,
        ),
    ) {
        MaterialTheme3(
            shapes = MaterialTheme3.shapes.copy(
                medium = Shapes.Shape12,
            ),
            colorScheme = MaterialTheme3.colorScheme.copy(
                background = Colors.White,
            ),
        ) {
            val bottomSheetNavigator = rememberBottomSheetNavigator(
                sheetState = rememberModalBottomSheetState(
                    initialValue = ModalBottomSheetValue.Hidden,
                    skipHalfExpanded = true,
                    confirmValueChange = { targetValue ->
                        targetValue != ModalBottomSheetValue.Hidden
                    },
                ),
            )

            val scaffoldState = rememberScaffoldState()
            val navController = rememberNavController(bottomSheetNavigator)

            CompositionLocalProvider(
                LocalNavController provides navController,
            ) {
                ModalBottomSheetLayout(
                    bottomSheetNavigator = bottomSheetNavigator,
                    scrimColor = Color.White,
                    sheetShape = Shapes.Shape24,
                ) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        bottomBar = { BottomNavigationBar(navController) }
                    ) {
                        Box(
                            modifier = Modifier.padding(it),
                            content = { content() },
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun rememberBottomSheetNavigator(
    sheetState: ModalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden),
): BottomSheetNavigator {
    return remember(sheetState) { BottomSheetNavigator(sheetState) }
}