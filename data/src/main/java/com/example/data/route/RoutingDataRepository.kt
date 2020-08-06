package com.example.data.route

import com.example.data.HereMapApi
import com.example.data.RouteEntityMapper
import com.example.poimap.domain.route.LatLng
import com.example.poimap.domain.route.RoutingRepository
import io.reactivex.Single
import javax.inject.Inject
import com.example.poimap.domain.route.Route

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