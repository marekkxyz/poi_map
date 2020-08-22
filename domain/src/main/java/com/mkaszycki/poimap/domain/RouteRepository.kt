package com.mkaszycki.poimap.domain

import com.mkaszycki.poimap.domain.coordinates.LatLngDomain
import com.mkaszycki.poimap.domain.route.Route
import io.reactivex.Single

interface RouteRepository {
    fun getRoute(origin: LatLngDomain, destination: LatLngDomain): Single<Route>
}