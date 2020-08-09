package com.mkaszycki.poimap.location

import com.google.android.gms.maps.model.LatLng
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable

interface LocationListener {
    fun onLocationUpdated(): Flowable<LatLng>
    fun start(): Disposable
}