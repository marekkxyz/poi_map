package com.example.data

import com.google.gson.annotations.SerializedName

data class PoiImageResponse(@SerializedName("query") val query: PoiImageQuery)

data class PoiImageQuery(@SerializedName("pages") val pages: Map<String, PoiImagePage>)

data class PoiImagePage(@SerializedName("imageinfo") val images: List<PoiImageUrl>)

data class PoiImageUrl(@SerializedName("url") val url: String)