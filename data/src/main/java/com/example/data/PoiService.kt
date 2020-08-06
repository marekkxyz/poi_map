package com.example.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PoiService {
    @GET("api.php")
    fun fetchPois(
        @Query("action") action: String = "query",
        @Query("list") list: String = "geosearch",
        @Query("gsradius") radius: Int,
        @Query("gscoord") cords: String,
        @Query("gslimit") limit: Int,
        @Query("format") format: String = "json"
    ): Single<PoisResponse>

    @GET("api.php")
    fun fetchPoi(
        @Query("action") action: String = "query",
        @Query("prop") prop: String,
        @Query("pageids") id: Int,
        @Query("format") format: String = "json"
    ): Single<PoiResponse>

    @GET("api.php")
    fun fetchPoiImages(
        @Query("action") action: String = "query",
        @Query("titles") fileName: String,
        @Query("prop") prop: String = "imageinfo",
        @Query("iiprop") iiprop: String = "url",
        @Query("format") format: String = "json"
    ): Single<PoiImageResponse>

}