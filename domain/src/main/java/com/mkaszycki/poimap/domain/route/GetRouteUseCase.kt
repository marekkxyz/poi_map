package com.mkaszycki.poimap.domain.route

import com.mkaszycki.poimap.domain.RoutingRepository
import com.mkaszycki.poimap.domain.coordinates.LatLngDomain
import io.reactivex.Single
import javax.inject.Inject

class GetRouteUseCase @Inject constructor(private val routingRepository: RoutingRepository) {
    fun run(origin: LatLngDomain, destination: LatLngDomain): Single<Route> =
        routingRepository.getRoute(origin, destination)
}