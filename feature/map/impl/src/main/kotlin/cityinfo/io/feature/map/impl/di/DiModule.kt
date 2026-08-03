package cityinfo.io.feature.map.impl.di

import cityinfo.io.feature.map.api.repositories.CityMapRepository
import cityinfo.io.feature.map.api.usecases.GetCityMapUseCase
import cityinfo.io.feature.map.impl.interactors.CityMapInteractor
import cityinfo.io.feature.map.impl.interactors.CityMapInteractorImpl
import cityinfo.io.feature.map.impl.interactors.MapManagerImpl
import cityinfo.io.feature.map.impl.repositories.CityMapRepositoryImpl
import cityinfo.io.feature.map.impl.screens.map.CityMapStore
import cityinfo.io.feature.map.impl.usecases.GetCityMapUseCaseImpl
import cityinfo.io.core.map.api.managers.MapManager
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val cityMapDiModule = module {
    singleOf(::MapManagerImpl).bind<MapManager>()

    singleOf(::GetCityMapUseCaseImpl).bind<GetCityMapUseCase>()
    singleOf(::CityMapRepositoryImpl).bind<CityMapRepository>()

    singleOf(::CityMapInteractorImpl).bind<CityMapInteractor>()
    viewModelOf(::CityMapStore)
}