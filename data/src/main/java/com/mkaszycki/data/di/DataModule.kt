package com.mkaszycki.data.di

import com.mkaszycki.data.PoiRepositoryImpl
import com.mkaszycki.data.RoutingRepositoryImpl
import com.mkaszycki.data.api.heremap.RoutingService
import com.mkaszycki.data.api.wikipedia.PoiService
import com.mkaszycki.poimap.domain.PoiRepository
import com.mkaszycki.poimap.domain.route.RoutingRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {
    @Provides
    fun providesPoiRepository(poiService: PoiService): PoiRepository = PoiRepositoryImpl(poiService)

    @Provides
    fun providesRoutingRepository(routingService: RoutingService): RoutingRepository =
        RoutingRepositoryImpl(routingService)
}