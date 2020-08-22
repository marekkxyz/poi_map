package com.mkaszycki.poimap.domain

import com.mkaszycki.poimap.domain.helper.LatLngDomain
import com.mkaszycki.poimap.domain.poidetails.PoiDetails
import com.mkaszycki.poimap.domain.pois.Poi
import io.reactivex.Single

interface PoiRepository {
    fun getPois(position: LatLngDomain): Single<List<Poi>>
    fun getPoiDetails(poiId: Int): Single<PoiDetails>
}