package com.mkaszycki.poimap.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mkaszycki.poimap.domain.poidetails.GetPoiDetailsUseCase
import com.mkaszycki.poimap.domain.pois.GetPoisUseCase
import com.mkaszycki.poimap.domain.route.GetRouteUseCase
import com.mkaszycki.poimap.location.LocationListener
import com.mkaszycki.poimap.ui.map.models.PoiDetailsMapper
import com.mkaszycki.poimap.ui.map.models.PoiMapper
import com.mkaszycki.poimap.ui.map.models.RouteMapper

class MapViewModelFactory(
    private val getPoisUseCase: GetPoisUseCase,
    private val getPoiDetailsUseCase: GetPoiDetailsUseCase,
    private val poiMapper: PoiMapper,
    private val poiDetailsMapper: PoiDetailsMapper,
    private val locationListener: LocationListener,
    private val getRouteUseCase: GetRouteUseCase,
    private val routeMapper: RouteMapper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MapViewModel::class.java) -> MapViewModel(
                getPoisUseCase,
                getPoiDetailsUseCase,
                poiMapper,
                poiDetailsMapper,
                locationListener,
                getRouteUseCase,
                routeMapper
            ) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}