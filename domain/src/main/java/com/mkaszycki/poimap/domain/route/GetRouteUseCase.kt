package com.mkaszycki.poimap.domain.route

import com.mkaszycki.poimap.domain.RouteRepository
import com.mkaszycki.poimap.domain.coordinates.LatLngDomain
import io.reactivex.Single
import javax.inject.Inject

class GetRouteUseCase @Inject constructor(private val routeRepository: RouteRepository) {
    fun run(origin: LatLngDomain, destination: LatLngDomain): Single<Route> =
        routeRepository.getRoute(origin, destination)
}