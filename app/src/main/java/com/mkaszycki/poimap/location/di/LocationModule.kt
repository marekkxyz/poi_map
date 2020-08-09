package com.mkaszycki.poimap.location.di

import android.content.Context
import com.mkaszycki.poimap.location.LocationListener
import com.mkaszycki.poimap.location.LocationManager
import dagger.Module
import dagger.Provides

@Module
class LocationModule {
    @Provides
    fun providesLocationListener(context: Context): LocationListener = LocationManager(context)
}