package com.mkaszycki.poimap.domain.route

import io.reactivex.Single
import javax.inject.Inject

class GetRoute @Inject constructor(private val routingRepository: RoutingRepository) {
    fun run(origin: LatLng, destination: LatLng): Single<Route> {
        return routingRepository.getRoute(origin, destination)
    }
}