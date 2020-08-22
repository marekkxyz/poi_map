package com.mkaszycki.data

import com.mkaszycki.data.api.heremap.RoutingService
import com.mkaszycki.data.api.heremap.response.toDomainRoute
import com.mkaszycki.poimap.domain.RoutingRepository
import com.mkaszycki.poimap.domain.coordinates.LatLngDomain
import com.mkaszycki.poimap.domain.route.Route
import io.reactivex.Single
import javax.inject.Inject

class RoutingRepositoryImpl @Inject constructor(
    private val routingService: RoutingService
) :
    RoutingRepository {
    override fun getRoute(origin: LatLngDomain, destination: LatLngDomain): Single<Route> {
        return routingService.getRoute(
            origin = "${origin.latitude},${origin.longitude}",
            destination = "${destination.latitude},${destination.longitude}",
            apiKey = BuildConfig.HEREMAP_API_KEY
        ).map { it.routes.first().sections.first().toDomainRoute() }
    }
}