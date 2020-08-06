package com.example.data

import com.example.poimap.domain.Poi
import com.example.poimap.domain.PoiDetails
import com.example.poimap.domain.PoiRepository
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class PoiDataRepository @Inject constructor(
    private val poiService: PoiService,
    private val poiEntityMapper: PoiEntityMapper
) :
    PoiRepository {
    override fun getPois(lat: Double, lng: Double): Single<List<Poi>> {
        return poiService.fetchPois(radius = radius, cords = "$lat|$lng", limit = limit)
            .map { poisResponse ->
                poisResponse.queryResult.pois.map { poiEntity ->
                    poiEntityMapper.map(poiEntity)
                }
            }
    }

    override fun getPoiDetails(id: Int): Single<PoiDetails> {
        return poiService.fetchPoi(prop = property, id = id).flatMap { poiResponse ->
            poiResponse.queryResult.pages["$id"]?.let { poiPage ->
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
                        PoiDetails(poiPage.title, poiPage.description, it) }
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