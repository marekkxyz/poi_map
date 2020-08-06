package com.example.poimap

import com.example.data.di.DataModule
import com.example.data.di.NetworkModule
import com.example.data.route.di.RoutingModule
import com.example.poimap.location.di.LocationModule
import com.example.poimap.ui.map.di.MapActivityModule
import com.example.poimap.ui.map.di.MapModule
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
        NetworkModule::class,
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