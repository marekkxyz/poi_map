package com.mkaszycki.data.api.heremap.response

import com.google.gson.annotations.SerializedName
import com.mkaszycki.poimap.domain.coordinates.LatLngDomain
import com.mkaszycki.poimap.domain.route.PolylineEncoderDecoder
import com.mkaszycki.poimap.domain.route.Route

data class RoutingResponse(@SerializedName("routes") val routes: List<RoutingItem>)

data class RoutingItem(@SerializedName("sections") val sections: List<RouteEntity>)

data class RouteEntity(
    @SerializedName("polyline") val polyline: String,
    @SerializedName("actions") val actions: List<ActionItem>
)

data class ActionItem(@SerializedName("instruction") val instruction: String)

fun RouteEntity.toDomainRoute(): Route {
    // Map points form LatLngZ to domain LatLng
    val routePoints = PolylineEncoderDecoder.decode(polyline)
        .map { pointZ: PolylineEncoderDecoder.LatLngZ -> LatLngDomain(pointZ.lat, pointZ.lng) }
    val suggestions = actions.map { it.instruction }
    return Route(routePoints, suggestions)
}