package com.example.poimap.ui.map.models

import com.example.poimap.domain.Mapper
import com.example.poimap.domain.Poi
import com.example.poimap.domain.PoiDetails
import com.example.poimap.domain.route.Route
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

/**
 * Map domain #Poi into application #PoiModel
 */
class PoiMapper @Inject constructor() : Mapper<Poi, PoiModel> {
    override fun map(obj: Poi): PoiModel {
        return with(obj) {
            PoiModel(
                id,
                LatLng(lat, lng),
                title
            )
        }
    }
}

/**
 * Map domain #PoiDetails into application #PoiDetailsModel
 */
class PoiDetailsMapper @Inject constructor() : Mapper<PoiDetails, PoiDetailsModel> {
    override fun map(obj: PoiDetails): PoiDetailsModel {
        return with(obj) {
            PoiDetailsModel(
                title,
                description,
                images
            )
        }
    }
}

class RouteMapper @Inject constructor() : Mapper<Route, RouteModel> {
    override fun map(obj: Route): RouteModel {
        return with(obj) {
            // Map points from to 'com.google.android.gms.maps.model.LatLng'
            val points = this.points.map { LatLng(it.lat, it.lng) }
            RouteModel(points, suggestions)
        }
    }
}