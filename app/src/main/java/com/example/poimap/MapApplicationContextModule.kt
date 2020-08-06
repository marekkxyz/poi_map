package com.example.poimap


import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MapApplicationContextModule {

    @Provides
    @Singleton
    fun providesApplication(app: MapApplication): Application = app

    @Provides
    @Singleton
    fun providesApplicationContext(app: Application): Context = app
}