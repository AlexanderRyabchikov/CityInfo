package cityinfo.io.feature.city.impl.di

import cityinfo.io.feature.city.api.managers.CityManager
import cityinfo.io.feature.city.api.repositories.CityRepository
import cityinfo.io.feature.city.api.usecases.GetCityListUseCase
import cityinfo.io.feature.city.impl.interactors.CityDetailInteractor
import cityinfo.io.feature.city.impl.interactors.CityDetailInteractorImpl
import cityinfo.io.feature.city.impl.interactors.CityListInteractor
import cityinfo.io.feature.city.impl.interactors.CityListInteractorImpl
import cityinfo.io.feature.city.impl.managers.CityManagerImpl
import cityinfo.io.feature.city.impl.repositories.CityRepositoryImpl
import cityinfo.io.feature.city.impl.screens.cities.CitiesStore
import cityinfo.io.feature.city.impl.screens.detail.CityDetailStore
import cityinfo.io.feature.city.impl.usecases.GetCityListUseCaseImpl
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val cityDiModule = module {
    singleOf(::CityManagerImpl).bind<CityManager>()
    singleOf(::GetCityListUseCaseImpl).bind<GetCityListUseCase>()
    singleOf(::CityRepositoryImpl).bind<CityRepository>()

    singleOf(::CityListInteractorImpl).bind<CityListInteractor>()
    viewModelOf(::CitiesStore)

    singleOf(::CityDetailInteractorImpl).bind<CityDetailInteractor>()
    viewModelOf(::CityDetailStore)
}