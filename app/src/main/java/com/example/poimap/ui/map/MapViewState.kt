package com.example.poimap.ui.map

import com.example.poimap.ui.map.models.PoiDetailsModel
import com.example.poimap.ui.map.models.PoiModel
import com.example.poimap.ui.map.models.RouteModel

sealed class MapViewState

data class GetPoisState(val pois: List<PoiModel>) : MapViewState()
data class GetPoiDetailsState(val details: PoiDetailsModel) : MapViewState()
data class GetRouteState(val routeModel: RouteModel) : MapViewState()
data class ErrorState(val type: MapErrorType) : MapViewState()