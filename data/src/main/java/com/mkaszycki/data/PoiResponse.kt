package com.mkaszycki.data

import com.google.gson.annotations.SerializedName

data class PoiResponse(
    @SerializedName("query") val queryResult: PoiPages
)

data class PoiPages(
    @SerializedName("pages") val pages: Map<String, PoiPage>
)

data class PoiPage(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("images") val images: List<PoiImage>
)

data class PoiImage(@SerializedName("title") val fileName: String)