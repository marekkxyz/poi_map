package com.mkaszycki.poimap.domain.pois

import com.mkaszycki.poimap.domain.PoiRepository
import com.mkaszycki.poimap.domain.coordinates.LatLngDomain
import io.reactivex.Single
import javax.inject.Inject

class GetPoisUseCase @Inject constructor(private val poiRepository: PoiRepository) {
    fun run(position: LatLngDomain): Single<List<Poi>> = poiRepository.getPois(position)
}