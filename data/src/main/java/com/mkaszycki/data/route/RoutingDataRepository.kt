package com.mkaszycki.data.route

import com.mkaszycki.data.HereMapApi
import com.mkaszycki.data.RouteEntityMapper
import com.mkaszycki.poimap.domain.route.LatLng
import com.mkaszycki.poimap.domain.route.RoutingRepository
import io.reactivex.Single
import javax.inject.Inject
import com.mkaszycki.poimap.domain.route.Route

class RoutingDataRepository @Inject constructor(
    private val routingService: RoutingService,
    private val routeEntityMapper: RouteEntityMapper
) :
    RoutingRepository {
    override fun getRoute(origin: LatLng, destination: LatLng): Single<Route> {
        return routingService.getRoute(
            origin = "${origin.lat},${origin.lng}",
            destination = "${destination.lat},${destination.lng}",
            apiKey = HereMapApi.apiKey
        ).map { routingResponse ->
            routeEntityMapper.map(
                routingResponse.routes.first().sections.first()
            )
        }
    }
}