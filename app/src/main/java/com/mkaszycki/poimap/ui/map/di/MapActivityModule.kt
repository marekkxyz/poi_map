package com.mkaszycki.poimap.ui.map.di

import com.mkaszycki.poimap.ui.map.MapActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MapActivityModule {
    @ContributesAndroidInjector
    abstract fun providesMapsActivity(): MapActivity
}