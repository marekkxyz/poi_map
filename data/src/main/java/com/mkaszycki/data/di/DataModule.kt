package com.mkaszycki.data.di

import com.mkaszycki.data.PoiDataRepository
import com.mkaszycki.data.PoiEntityMapper
import com.mkaszycki.data.PoiService
import com.mkaszycki.data.RouteEntityMapper
import com.mkaszycki.data.route.RoutingDataRepository
import com.mkaszycki.data.route.RoutingService
import com.mkaszycki.poimap.domain.PoiRepository
import com.mkaszycki.poimap.domain.route.RoutingRepository
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