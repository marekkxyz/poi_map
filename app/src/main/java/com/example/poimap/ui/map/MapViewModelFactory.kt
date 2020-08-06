package com.example.poimap.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.poimap.domain.GetPoiDetailsUseCase
import com.example.poimap.domain.GetPoisUseCase
import com.example.poimap.domain.route.GetRoute
import com.example.poimap.location.LocationListener
import com.example.poimap.ui.map.models.PoiDetailsMapper
import com.example.poimap.ui.map.models.PoiMapper
import com.example.poimap.ui.map.models.RouteMapper

class MapViewModelFactory(
    private val getPoisUseCase: GetPoisUseCase,
    private val getPoiDetailsUseCase: GetPoiDetailsUseCase,
    private val poiMapper: PoiMapper,
    private val poiDetailsMapper: PoiDetailsMapper,
    private val locationListener: LocationListener,
    private val getRoute: GetRoute,
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
                getRoute,
                routeMapper
            ) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}