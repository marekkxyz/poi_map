package com.example.poimap.ui.map.di

import com.example.poimap.domain.GetPoiDetailsUseCase
import com.example.poimap.domain.GetPoisUseCase
import com.example.poimap.domain.route.GetRoute
import com.example.poimap.location.LocationListener
import com.example.poimap.ui.map.MapViewModelFactory
import com.example.poimap.ui.map.models.PoiDetailsMapper
import com.example.poimap.ui.map.models.PoiMapper
import com.example.poimap.ui.map.models.RouteMapper
import dagger.Module
import dagger.Provides

@Module
class MapModule {
    @Provides
    fun providesMapViewModelFactory(
        getPoisUseCase: GetPoisUseCase,
        getPoiDetailsUseCase: GetPoiDetailsUseCase,
        poiMapper: PoiMapper,
        poiDetailsMapper: PoiDetailsMapper,
        locationListener: LocationListener,
        getRoute: GetRoute,
        routeMapper: RouteMapper
    ): MapViewModelFactory = MapViewModelFactory(
        getPoisUseCase,
        getPoiDetailsUseCase,
        poiMapper,
        poiDetailsMapper,
        locationListener,
        getRoute,
        routeMapper
    )
}