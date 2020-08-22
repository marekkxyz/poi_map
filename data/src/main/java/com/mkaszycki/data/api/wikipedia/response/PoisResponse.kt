package com.mkaszycki.data.api.wikipedia.response

import com.google.gson.annotations.SerializedName
import com.mkaszycki.poimap.domain.pois.Poi

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

fun PoiEntity.toDomainPoi(): Poi =
    Poi(id, latitude, longitude, title)