package com.example.poimap.ui.map

import androidx.lifecycle.MutableLiveData
import com.example.poimap.domain.GetPoiDetailsUseCase
import com.example.poimap.domain.GetPoisUseCase
import com.example.poimap.domain.route.GetRoute
import com.example.poimap.location.LocationListener
import com.example.poimap.toDomain
import com.example.poimap.ui.BaseViewModel
import com.example.poimap.ui.map.models.PoiDetailsMapper
import com.example.poimap.ui.map.models.PoiMapper
import com.example.poimap.ui.map.models.PoiModel
import com.example.poimap.ui.map.models.RouteMapper
import com.google.android.gms.maps.model.LatLng
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MapViewModel(
    private val getPoisUseCase: GetPoisUseCase,
    private val getPoiDetailsUseCase: GetPoiDetailsUseCase,
    private val poiMapper: PoiMapper,
    private val poiDetailsMapper: PoiDetailsMapper,
    private val location: LocationListener,
    private val getRoute: GetRoute,
    private val routeMapper: RouteMapper
) : BaseViewModel() {

    val state = MutableLiveData<MapViewState>()

    private var currentPosition: LatLng? = null
    private var lastSelectedPoi: PoiModel? = null

    fun getPois() {
        location.start().bindToLifecycle()
        location.onLocationUpdated()
            .flatMapSingle { position: LatLng ->
                currentPosition = position
                position.run {
                    getPoisUseCase.run(latitude, longitude)
                }
            }
            .map { it.map(poiMapper::map) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { state.value = GetPoisState(it) },
                onError = {
                    state.value = ErrorState(MapErrorType.GET_POI_ERROR)
                    Timber.tag(tag).e(it,"getPois : Error")
                }
            ).bindToLifecycle()
    }

    fun getPoiDetails(poi: PoiModel) {
        lastSelectedPoi = poi
        getPoiDetailsUseCase.run(poi.id)
            .map { poiDetailsMapper.map(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { state.value = GetPoiDetailsState(it) },
                onError = {
                    state.value = ErrorState(MapErrorType.GET_POI_DETAILS_ERROR)
                    Timber.tag(tag).e(it, "getPoiDetails : Error")
                }
            )
            .bindToLifecycle()
    }

    fun getRoute() {
        if (currentPosition == null || lastSelectedPoi == null) {
            Timber.tag(tag).e("currentPosition or lastSelectedPoi is null")
            state.value = ErrorState(MapErrorType.GET_ROUTE_ERROR)
            return
        }

        getRoute.run(currentPosition!!.toDomain(), lastSelectedPoi!!.latLng.toDomain())
            .map { routeMapper.map(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { state.value = GetRouteState(it) },
                onError = {
                    state.value = ErrorState(MapErrorType.GET_ROUTE_ERROR)
                    Timber.tag(tag).e(it, "getRoute: Error")
                }
            ).bindToLifecycle()
    }

    companion object {
        const val tag = "MapViewModel";
    }
}