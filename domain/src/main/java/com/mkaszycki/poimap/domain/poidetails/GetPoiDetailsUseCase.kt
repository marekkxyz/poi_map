package com.mkaszycki.poimap.domain.poidetails

import com.mkaszycki.poimap.domain.PoiRepository
import io.reactivex.Single
import javax.inject.Inject

class GetPoiDetailsUseCase @Inject constructor(private val poiRepository: PoiRepository) {
    fun run(poiId: Int): Single<PoiDetails> = poiRepository.getPoiDetails(poiId)
}