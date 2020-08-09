package com.mkaszycki.data

import com.google.gson.annotations.SerializedName

data class PoisResponse(
    @SerializedName("query") val queryResult: Geosearch
)

data class Geosearch(
    @SerializedName("geosearch") val pois: List<PoiEntity>
)

data class PoiEntity(
    @SerializedName("pageid") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lon") val longitude: Double
)