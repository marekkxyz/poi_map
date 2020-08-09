package com.mkaszycki.poimap.ui.map.di

import com.mkaszycki.poimap.domain.GetPoiDetailsUseCase
import com.mkaszycki.poimap.domain.GetPoisUseCase
import com.mkaszycki.poimap.domain.route.GetRoute
import com.mkaszycki.poimap.location.LocationListener
import com.mkaszycki.poimap.ui.map.MapViewModelFactory
import com.mkaszycki.poimap.ui.map.models.PoiDetailsMapper
import com.mkaszycki.poimap.ui.map.models.PoiMapper
import com.mkaszycki.poimap.ui.map.models.RouteMapper
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