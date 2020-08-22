package com.mkaszycki.data

import com.mkaszycki.data.api.wikipedia.PoiService
import com.mkaszycki.data.api.wikipedia.response.toDomainPoi
import com.mkaszycki.poimap.domain.PoiRepository
import com.mkaszycki.poimap.domain.helper.LatLngDomain
import com.mkaszycki.poimap.domain.poidetails.PoiDetails
import com.mkaszycki.poimap.domain.pois.Poi
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class PoiRepositoryImpl @Inject constructor(
    private val poiService: PoiService
) :
    PoiRepository {
    override fun getPois(position: LatLngDomain): Single<List<Poi>> {
        return poiService.fetchPois(
            radius = radius,
            cords = "${position.latitude}|${position.longitude}",
            limit = limit
        ).map { poisResponse ->
            poisResponse.queryResult.pois.map { it.toDomainPoi() }
        }
    }

    override fun getPoiDetails(poiId: Int): Single<PoiDetails> {
        return poiService.fetchPoi(prop = property, id = poiId).flatMap { poiResponse ->
            poiResponse.queryResult.pages["$poiId"]?.let { poiPage ->
                val imagesUrl = poiPage.images.map {
                    fetchImageUrl(it.fileName)
                }

                Flowable.fromIterable(imagesUrl)
                    .flatMap {
                        it.toFlowable().onErrorReturn {
                            ""
                        }
                    }
                    .toList()
                    .map {
                        it.filter {
                            it.isNotBlank()
                        }
                    }
                    .map {
                        PoiDetails(
                            poiPage.title,
                            poiPage.description,
                            it
                        )
                    }
            }
        }
    }


    private fun fetchImageUrl(fileName: String): Single<String> {
        return poiService.fetchPoiImages(fileName = fileName)
            .map {
                it.query.pages.values.first()
            }
            .map {
                it.images.first().url
            }
    }

    companion object {
        const val tag = "PoiDataRepository"
        const val radius = 10000
        const val limit = 50
        const val property = "info|description|images"
    }
}