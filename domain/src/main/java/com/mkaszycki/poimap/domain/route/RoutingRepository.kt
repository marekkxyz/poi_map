package com.mkaszycki.poimap.domain.route

import io.reactivex.Single

interface RoutingRepository {
    fun getRoute(origin: LatLng, destination: LatLng): Single<Route>
}