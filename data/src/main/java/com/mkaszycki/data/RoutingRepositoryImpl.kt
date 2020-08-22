package com.mkaszycki.data

import com.mkaszycki.data.api.heremap.RoutingService
import com.mkaszycki.data.api.heremap.response.toDomainRoute
import com.mkaszycki.poimap.domain.route.LatLng
import com.mkaszycki.poimap.domain.route.Route
import com.mkaszycki.poimap.domain.route.RoutingRepository
import io.reactivex.Single
import javax.inject.Inject

class RoutingRepositoryImpl @Inject constructor(
    private val routingService: RoutingService
) :
    RoutingRepository {
    override fun getRoute(origin: LatLng, destination: LatLng): Single<Route> {
        return routingService.getRoute(
            origin = "${origin.lat},${origin.lng}",
            destination = "${destination.lat},${destination.lng}",
            apiKey = BuildConfig.HEREMAP_API_KEY
        ).map { it.routes.first().sections.first().toDomainRoute() }
    }
}