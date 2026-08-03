package cityinfo.io

import cityinfo.io.core.network.networkModule
import cityinfo.io.feature.city.impl.di.cityDiModule
import cityinfo.io.feature.map.impl.di.cityMapDiModule

internal val coreModules = listOf(
    networkModule
)

internal val featureModule = listOf(
    cityDiModule,
    cityMapDiModule
)

internal val koinAppModules = coreModules + featureModule