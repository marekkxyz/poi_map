package com.mkaszycki.data.route

import com.google.gson.annotations.SerializedName

data class RoutingResponse(@SerializedName("routes") val routes: List<RoutingItem>)

data class RoutingItem(@SerializedName("sections") val sections: List<RouteEntity>)

data class RouteEntity(
    @SerializedName("polyline") val polyline: String,
    @SerializedName("actions") val actions: List<ActionItem>
)

data class ActionItem(@SerializedName("instruction") val instruction: String)