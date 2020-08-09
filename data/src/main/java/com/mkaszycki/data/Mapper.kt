package com.mkaszycki.data

import com.mkaszycki.data.route.RouteEntity
import com.mkaszycki.poimap.domain.Mapper
import com.mkaszycki.poimap.domain.Poi
import com.mkaszycki.poimap.domain.route.LatLng
import com.mkaszycki.poimap.domain.route.PolylineEncoderDecoder
import com.mkaszycki.poimap.domain.route.Route
import javax.inject.Inject

/**
 * Map #PoiEntity into domain #Poi
 */
class PoiEntityMapper @Inject constructor() : Mapper<PoiEntity, Poi> {
    override fun map(obj: PoiEntity): Poi {
        return with(obj) {
            Poi(id, latitude, longitude, title)
        }
    }
}

class RouteEntityMapper @Inject constructor() : Mapper<RouteEntity, Route> {
    override fun map(obj: RouteEntity): Route {
        return with(obj) {
            // Map points form LatLngZ to domain LatLng
            val routePoints = PolylineEncoderDecoder.decode(polyline)
                .map { pointZ: PolylineEncoderDecoder.LatLngZ -> LatLng(pointZ.lat, pointZ.lng) }

            val suggestions = actions.map { it.instruction }

            Route(routePoints, suggestions)
        }
    }
}


