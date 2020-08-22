package com.mkaszycki.poimap.ui.map

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.mkaszycki.poimap.domain.poidetails.GetPoiDetailsUseCase
import com.mkaszycki.poimap.domain.pois.GetPoisUseCase
import com.mkaszycki.poimap.domain.route.GetRouteUseCase
import com.mkaszycki.poimap.location.LocationListener
import com.mkaszycki.poimap.toLatLngDomain
import com.mkaszycki.poimap.ui.BaseViewModel
import com.mkaszycki.poimap.ui.map.models.PoiDetailsMapper
import com.mkaszycki.poimap.ui.map.models.PoiMapper
import com.mkaszycki.poimap.ui.map.models.PoiModel
import com.mkaszycki.poimap.ui.map.models.RouteMapper
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
    private val getRouteUseCase: GetRouteUseCase,
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
                    getPoisUseCase.run(this.toLatLngDomain())
                }
            }
            .map { it.map(poiMapper::map) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { state.value = GetPoisState(it) },
                onError = {
                    state.value = ErrorState(MapErrorType.GET_POI_ERROR)
                    Timber.tag(tag).e(it, "getPois : Error")
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

        getRouteUseCase.run(
            currentPosition!!.toLatLngDomain(),
            lastSelectedPoi!!.latLng.toLatLngDomain()
        )
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