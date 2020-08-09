package com.mkaszycki.poimap.domain

import io.reactivex.Single
import javax.inject.Inject

class GetPoiDetailsUseCase @Inject constructor(private val poiRepository: PoiRepository) {
    fun run(id: Int): Single<PoiDetails> = poiRepository.getPoiDetails(id)
}