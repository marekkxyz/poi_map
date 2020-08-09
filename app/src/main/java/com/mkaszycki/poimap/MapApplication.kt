package com.mkaszycki.poimap

import com.mkaszycki.data.HereMapApi
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import timber.log.Timber.DebugTree

class MapApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerMapApplicationComponent.factory().newAppComponent(this)

    override fun onCreate() {
        super.onCreate()
        setupHereMaps()
        setupLogging()
    }

    private fun setupHereMaps() {
        HereMapApi.apiKey = getString(R.string.here_maps_api_key)
    }

    private fun setupLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}