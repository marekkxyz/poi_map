package com.example.poimap.location.di

import android.content.Context
import com.example.poimap.location.LocationListener
import com.example.poimap.location.LocationManager
import dagger.Module
import dagger.Provides

@Module
class LocationModule {
    @Provides
    fun providesLocationListener(context: Context): LocationListener = LocationManager(context)
}