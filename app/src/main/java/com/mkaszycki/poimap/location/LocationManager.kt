package com.mkaszycki.poimap.location

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider
import timber.log.Timber
import javax.inject.Inject

class LocationManager @Inject constructor(private val appContext: Context) : LocationListener {

    private val locationProcessor = BehaviorProcessor.create<LatLng>()

    override fun start(): Disposable {
        return ReactiveLocationProvider(appContext)
            .lastKnownLocation
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { locationProcessor.offer(LatLng(it.latitude, it.longitude)) },
                onError = { Timber.tag(tag).e(it, "Location update error") }
            )
    }

    override fun onLocationUpdated(): Flowable<LatLng> = locationProcessor

    companion object {
        const val tag = "LocationManager"
    }
}