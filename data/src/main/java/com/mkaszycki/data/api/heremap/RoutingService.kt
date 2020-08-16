package com.mkaszycki.data.api.heremap

import com.mkaszycki.data.api.heremap.response.RoutingResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RoutingService {
    @GET("routes")
    fun getRoute(
        @Query("transportMode") transportMode: String = "car",
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("return") returnVal: String = "polyline,actions,instructions",
        @Query("apiKey") apiKey: String
    ) : Single<RoutingResponse>
}