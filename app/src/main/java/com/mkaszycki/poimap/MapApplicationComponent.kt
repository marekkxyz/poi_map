package com.mkaszycki.poimap

import com.mkaszycki.data.di.DataModule
import com.mkaszycki.data.di.PoiModule
import com.mkaszycki.data.di.RoutingModule
import com.mkaszycki.poimap.location.di.LocationModule
import com.mkaszycki.poimap.ui.map.di.MapActivityModule
import com.mkaszycki.poimap.ui.map.di.MapModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        MapApplicationContextModule::class,
        LocationModule::class,
        PoiModule::class,
        RoutingModule::class,
        DataModule::class,
        MapModule::class,
        MapActivityModule::class
    ]
)
interface MapApplicationComponent : AndroidInjector<MapApplication> {

    @Component.Factory
    interface Factory {
        fun newAppComponent(@BindsInstance application: MapApplication): MapApplicationComponent
    }
}