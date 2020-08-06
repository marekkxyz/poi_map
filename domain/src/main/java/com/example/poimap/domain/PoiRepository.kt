package com.example.poimap.domain

import io.reactivex.Single

interface PoiRepository {
    fun getPois(lat: Double, lng: Double): Single<List<Poi>>
    fun getPoiDetails(id: Int): Single<PoiDetails>
}