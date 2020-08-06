package com.example.data.di

import com.example.data.PoiDataRepository
import com.example.data.PoiEntityMapper
import com.example.data.PoiService
import com.example.data.RouteEntityMapper
import com.example.data.route.RoutingDataRepository
import com.example.data.route.RoutingService
import com.example.poimap.domain.PoiRepository
import com.example.poimap.domain.route.RoutingRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {
    @Provides
    fun providesPoiRepository(
        poiService: PoiService,
        poiEntityMapper: PoiEntityMapper
    ): PoiRepository = PoiDataRepository(poiService, poiEntityMapper)

    @Provides
    fun providesRoutingRepository(
        routingService: RoutingService,
        routeEntityMapper: RouteEntityMapper
    ): RoutingRepository = RoutingDataRepository(routingService, routeEntityMapper)
}