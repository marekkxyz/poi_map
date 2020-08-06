package com.example.poimap.domain

import io.reactivex.Single
import javax.inject.Inject

class GetPoisUseCase @Inject constructor(private val poiRepository: PoiRepository) {
    fun run(lat: Double, lng: Double): Single<List<Poi>> = poiRepository.getPois(lat, lng)
}